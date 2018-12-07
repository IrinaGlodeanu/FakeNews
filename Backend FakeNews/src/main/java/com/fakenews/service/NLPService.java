package com.fakenews.service;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

        InputStream resourceAsStream = NLPService.class.getClassLoader().getResourceAsStream("com/fakenews/service/en-pos-maxent.bin");

        File file = new File("file.tmp");
        try {
            FileUtils.copyInputStreamToFile(resourceAsStream, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        model = new POSModelLoader().load(file);
    }

    public POSSample posTagging(String sentence) {

        POSTaggerME tagger = new POSTaggerME(model);

        String wholeText = sentence.replaceAll("\\r|\\n", " ");

        String regularExpression = "(((http|ftp|https):\\/\\/)?[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?)";

        wholeText = wholeText.replaceAll(regularExpression, "");
        String whitespaceTokenizerLine[] = wholeText.split("\\W+");
        String[] tags = tagger.tag(whitespaceTokenizerLine);

        return new POSSample(whitespaceTokenizerLine, tags);
    }

    public List<String> retrieveKeywords(String input) {
        POSSample posSample = posTagging(input);
        String[] tags = posSample.getTags();
        String[] sentence = posSample.getSentence();

        List<String> keywords = new ArrayList<>();
        for (int i = 0; i < tags.length-1 ; i++) {
            if (tags[i].startsWith("NN") && !sentence[i].isEmpty()) {
                keywords.add(sentence[i]);
            }
        }
        return keywords;
    }
}
