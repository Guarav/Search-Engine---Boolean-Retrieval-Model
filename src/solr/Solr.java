package solr;

import org.apache.commons.collections.CollectionUtils;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by gaurav on 5/27/14.
 */

/**
 * this is the main class which handles all the requests for indexing and querying
 */
public class Solr {

    private Indexer indexerInstance;

    private static final String filePath = "indexes.ser";

    public static void main(String [] args)
    {
        Scanner scanner = new Scanner(System.in);
        Solr solr = new Solr();
        System.out.println( "press 'A' for add and 'Q' for query" );
        while(true)
        {
            String input = scanner.nextLine();
            if(input.equals("A"))
            {
                // add new doc
                // take string as input

                String doc = scanner.nextLine();
                solr.indexDoc(doc.toLowerCase());


            }
            else if(input.equals("Q"))
            {
                // take query as input

                String query = scanner.nextLine();
                Set<String> docs = solr.getDocs(query.toLowerCase());
                int i = 1;
                for(String s : docs)
                {
                    System.out.println(i + "  " + s);
                    i++;
                }

                if(docs.size() == 0)
                   System.out.println("No results found!!");


            }
        }


    }

    public Solr()
    {
        // load indexerInstance;

        loadIndexes();
    }

    private void loadIndexes()
    {
        File file = new File(filePath);

        if(file.exists()){
            try{
                ObjectInputStream in = new ObjectInputStream(  new FileInputStream(file));
                indexerInstance = (Indexer)in.readObject();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            indexerInstance = new Indexer();
        }
    }

    public void indexDoc( String data )
    {

        // get tokens
        List<String> indexTokens = Tokeniser.getInstance().getTokens( data );

        int docId = indexerInstance.addDoc(data);

        for(String token : indexTokens)
            indexerInstance.addInvIndex( token, docId );

        // after indexing update file

        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(filePath)));
            out.writeObject(indexerInstance);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public Set<String> getDocs(String query)
    {
        Set<Integer> docIds = indexerInstance.getDocsForTerm( query.split("\\s+")[0] );
        for(String term : query.split("\\s+"))
        {
            docIds = new LinkedHashSet<Integer>(CollectionUtils.intersection(docIds, indexerInstance.getDocsForTerm(term))) ;
        }

        // get doc details on the basis of docIds
        Set<String> docs = new LinkedHashSet<String>();
        for(int id : docIds)
        {
            docs.add(indexerInstance.getDocFromId(id));
        }
        return docs;
    }


}
