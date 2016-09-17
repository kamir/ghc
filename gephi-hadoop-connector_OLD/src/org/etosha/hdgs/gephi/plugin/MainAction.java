/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.etosha.hdgs.gephi.plugin;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.etosha.hdgs.hive.HiveImportConnector; 
import org.etosha.hdgs.mysql.MySQLImportConnector;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "org.etosha.hdgs.gephi.plugin.MainAction")
@ActionRegistration(
        iconBase = "org/etosha/hdgs/gephi/plugin/treeSun16.png",
        displayName = "#CTL_MainAction")
@ActionReference(path = "Menu/File", position = 0, separatorBefore = -50, separatorAfter = 50)
@Messages("CTL_MainAction=HadoopConnectorMainAction")
public final class MainAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        String[] options = new String[] {"Hive", "Impala", "MySQL", "SMW", "Rexter" };
        int opt = JOptionPane.showOptionDialog(null, "Title", "Message", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options, options[0]);
        
        javax.swing.JOptionPane.showMessageDialog(null,"Go ... " + opt);
        
        switch( opt ) { 
            
            case 4 : {
                break;
            } 
              
            case 3 : {
                break;
            }
                
            
            case 2 : {
                MySQLImportConnector.main( null );
                break;
            } 
                
            
            case 1 : { 
                break;
            } 
            
            case 0 : {
                HiveImportConnector.main( null );
                break;
            } 
                    
        
        }

        
    }
}
