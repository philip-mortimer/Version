/*
 * Copyright (C) 2019 Philip Mortimer
 *
 * This file is part of Philip Mortimer Example Programs.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package version;

import java.util.ArrayList;
import java.util.Collections;

public class VersionTest {
    private static String createErrorString(final Version version1, 
                                            final Version version2) {
        String errorStrings;
        if (version1.isErrorDetected() && version2.isErrorDetected()) {
            errorStrings = version1.getErrorString() + "; "
                    + version2.getErrorString();            
        }
        else if (version1.isErrorDetected()) {
            errorStrings = version1.getErrorString();               
        }
        else {
            errorStrings = version2.getErrorString();                           
        }
        String errorString = String.format("Error comparing '%s' and '%s': %s", 
                                           version1, 
                                           version2, 
                                           errorStrings);
        return errorString;
    }
    
    public static void compare(String versionStr1, String versionStr2) {
        Version version1 = new Version(versionStr1);
        Version version2 = new Version(versionStr2);

        String resultStr;
        if (version1.isErrorDetected() || version2.isErrorDetected()) {
            String errorString = createErrorString(version1, version2);
            System.out.println(errorString);
            return;
        }
            
        if (version1.compareTo(version2) > 0) {
            resultStr = String.format("%s > %s", versionStr1, versionStr2);
            
        }
        else if (version1.compareTo(version2) < 0) {
            resultStr = String.format("%s < %s", versionStr1, versionStr2);            
        }
        else {
            resultStr = String.format("%s==%s", versionStr1, versionStr2);                        
        }
        System.out.println(resultStr);
    }
    
    public static void compareTest() {
        // Valid cases.
        compare("1.2.9", "1.3");
        compare("1.5.0", "1.5.0.1");
        compare("5", "3");
        compare("4.3", "4.2");
        compare("1.5.0", "1.4.0.1");
        compare("1.3", "1.3");
        compare("5.2", "5.2.0");
        compare("000000000000000000000000000078.1", "78.1");
        
        // Error cases.
        compare("5.2.0", "");
        compare("5..0", "1");
        compare("1.", "1");
        compare("5.3", ".2");
        compare("100000078.1", "78.2");        
    }
    
    public static void printVersionList(ArrayList<Version> versionList) {
        for (Version version: versionList) {
            System.out.println(version);
        }
    }
    
    public static void sortTest() {
        ArrayList<Version> versionList = new ArrayList<>();
        
        versionList.add(new Version("5.0"));
        versionList.add(new Version("4.0.1"));
        versionList.add(new Version("6.3.0.11"));
        versionList.add(new Version("3"));
        
        System.out.println("\nVersions before sort:");
        printVersionList(versionList);
        
        Collections.sort(versionList);

        System.out.println("\nVersions after sort:");
        printVersionList(versionList);        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        compareTest();
        sortTest();
    }

    
}
