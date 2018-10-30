/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiapiclient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SnippetLoader {

    WikiORIGINAL wiki = null;

    public SnippetLoader() {
    }

    public void init(WikiORIGINAL wikiServer) {
        wiki = wikiServer;
    }

    /**
     * Velocity-Snippets are stored in the WikiSpace. So they are editable 
     * by all users.
     * 
     * @param name
     * @param f
     * @return 
     */
    public String _loadVelocityTemplate(final String name, File f, boolean debug) {
        String s = "";
        try {

            System.out.println(">>> Velocity-Snippet: " + name);

            // String statuss = wiki.getPageInfo(name).toString();
            s = wiki.getPageText(name);

            s = s.replace("<nowiki>", " ");
            s = s.replace("</nowiki>", " ");

            if ( debug )    
                System.out.println(s);

            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(s);

            bw.flush();

            System.out.println(">>> Done." );

        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return s;
    }

}
