package solr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 5/27/14.
 */
public class Tokeniser {


    private static Tokeniser instance = null;

    public static void main(String [] args)
    {

    }

    public static Tokeniser getInstance()
    {
        if( instance == null )
            instance = new Tokeniser();
        return instance;
    }


    public List<String> getTokens( String data )
    {
        List<String> tokens = new ArrayList<String>();
        String []terms = data.split("\\s+");
        for(String term : terms)
        {
            System.out.println(term);
            tokens.add(term);
        }

        return tokens;
    }


}
