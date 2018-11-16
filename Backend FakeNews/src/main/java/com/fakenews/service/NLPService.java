package com.fakenews.service;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class NLPService {

    public static final String input = "Pres. Trump, first lady Melania\n" +
            " Trump arrive in France\n" +
            " for ceremony marking centennial anniversary of the end of World War I.";


    public POSSample posTagging(String sentence) throws IOException {

        POSModel model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
        PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
        POSTaggerME tagger = new POSTaggerME(model);

        ObjectStream<String> lineStream =
                new PlainTextByLineStream(new StringReader(sentence));

        perfMon.start();
        String line, wholeText = "";
        while ((line = lineStream.read()) != null) {
            wholeText = wholeText + " " + line;
        }

        String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(wholeText);
        String[] tags = tagger.tag(whitespaceTokenizerLine);

        POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
        System.out.println(sample.toString());

        perfMon.incrementCounter();

        perfMon.stopAndPrintFinalResult();
        return sample;
    }

    public List<String> retrieveKeywords() throws IOException {
            POSSample posSample = posTagging(input);
            String[] tags = posSample.getTags();
            String[] sentence = posSample.getSentence();

            List<String> keywords = new ArrayList<>();
        for (int i=0; i<tags.length; i++){
            if(tags[i].startsWith("NN")){
                keywords.add(sentence[i].replaceAll("[,]", ""));
            }
        }
        return keywords;
    }


    public static void main(String[] args) throws IOException {
        NLPService nlpService = new NLPService();
        System.out.println(nlpService.retrieveKeywords());
    }
}
