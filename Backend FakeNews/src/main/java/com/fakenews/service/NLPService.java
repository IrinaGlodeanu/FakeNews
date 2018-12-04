package com.fakenews.service;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NLPService {

    public static final String testinput = "Pres. Trump, first lady Melania\n" +
            " Trump arrive in France\n" +
            " for ceremony marking centennial anniversary of the end of World War I.";
    public static final String testinput2 = "How George H.W. Bush became Beijing's old friend in the White House https://cnn.it/2E65SnO" ;

    private final POSModel model;

    public NLPService() {
        model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
    }

    public POSSample posTagging(String sentence) throws IOException {

        POSTaggerME tagger = new POSTaggerME(model);

        String wholeText = sentence.replaceAll("\\r|\\n", " ");

        String regularExpression = "(((http|ftp|https):\\/\\/)?[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?)";

        wholeText = wholeText.replaceAll(regularExpression, "");
        String whitespaceTokenizerLine[] = wholeText.split("\\W+");
        String[] tags = tagger.tag(whitespaceTokenizerLine);

        return new POSSample(whitespaceTokenizerLine, tags);
    }

    public List<String> retrieveKeywords(String input) throws IOException {
        POSSample posSample = posTagging(input);
        String[] tags = posSample.getTags();
        String[] sentence = posSample.getSentence();

        List<String> keywords = new ArrayList<>();
        for (int i = 0; i < tags.length-1 ; i++) {
            if (tags[i].startsWith("NN") && !sentence[i].startsWith("http") && !sentence[i].isEmpty()) {
                keywords.add(sentence[i]);
            }
        }
        return keywords;
    }

    public static void main(String[] args) throws IOException {
        NLPService nlpService = new NLPService();
        System.out.println(nlpService.retrieveKeywords(testinput2));
    }
}
