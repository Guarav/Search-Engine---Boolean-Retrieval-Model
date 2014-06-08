package solr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by gaurav on 5/27/14.
 */

/**
 * this handles the logic of creating the tokens out of doc(string)
 */
public class Tokeniser {


    private static Tokeniser instance = null;

    private static final String stopWordsFilePath = "stoplist.txt";

    private Set<String> stopWordsList = new HashSet<String>();

    public static void main(String [] args)
    {

    }

    public static Tokeniser getInstance()
    {
        if( instance == null )
            instance = new Tokeniser();
        return instance;
    }

    public Tokeniser()
    {
        //load stop words list
        loadStopWords();
    }

    private void loadStopWords()
    {
        File file = new File(stopWordsFilePath);

        if(file.exists()){

            try{
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line = "";
                while( (line = bufferedReader.readLine()) != null )
                    stopWordsList.add(line.trim());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    public List<String> getTokens( String data )
    {
        List<String> tokens = new ArrayList<String>();
        String []terms = data.split("\\s+");
        for(String term : terms)
        {
            if( !this.stopWordsList.contains(term) )
            {
                System.out.println(term);
                tokens.add(term);
            }

        }

        return tokens;
    }


}
