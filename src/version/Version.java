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

/**
 * Stores a version (e.g. 5.2.11) as a sequence of integer version numbers.
 */
public class Version implements Comparable<Version> {
    static private final int MAX_VERSION_NUMBER = (int)1e8;
    
    private final ArrayList<Integer> versionNumbers;
    private final String versionStr;
    
    private boolean errorDetected;
    private String errorString;
        
    private void setError(final String errorString) {
        this.errorDetected = true;
        this.errorString = errorString;        
    }

    private boolean checkVersionNumberSize(int versionNumber, 
                                           final String versionStr) {        
        if (versionNumber > MAX_VERSION_NUMBER) {
            String errStr = String.format(
                    "'%s' contains version num (%d) > max supported (%d)", 
                    versionStr, 
                    versionNumber, 
                    MAX_VERSION_NUMBER);
            this.setError(errStr);
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * @param versionStr the version as a string e.g. "5.0.1"
     */
    public Version(final String versionStr) {
        this.versionNumbers = new ArrayList<>();
        this.versionStr = versionStr;
        this.errorDetected = false;
        this.errorString = "";
        
        int i = 0;
        int arrayPos = 0;
        while (true) {
            int n = 0;
            boolean numberFound = false;
            while (i < versionStr.length()) {
                char ch = versionStr.charAt(i);
                if (ch == '.') {
                    // Version number separator found.
                    break;
                }
                if (ch < '0' || ch > '9') {
                    this.setError("'" + versionStr 
                                  + "' not valid or supported version");
                    return;
                }
                numberFound = true;
                n = n * 10 + ch - '0';
                if (! this.checkVersionNumberSize(n, versionStr)) {
                    return;
                }
                i++;
            }
            if (! numberFound) {
                this.setError("'" + versionStr 
                              + "' contains missing version number(s)");
                return;
            }
            this.versionNumbers.add(n);
            if (i == versionStr.length()) {
                break;
            }
            i++;
        }
    }

    /**
     * Returns the version number from the sequence of version numbers at the 
     * location specified by index where index starts at 0 e.g. if the version
     * is 4.12 then getVersionNumber(0) will return 4 and getVersionNumber(1)
     * will return 12. If index is greater than or equal to the number of 
     * version numbers then getVersionNumber will return 0.
     */
    public int getVersionNumber(int index) {
        if (index < this.versionNumbers.size()) {
            return this.versionNumbers.get(index);
        }
        else {
            return 0;
        }
    }

    /**
     * @return the number of version numbers in the version e.g. if the version
     * is 5.3.11 then it will return 3
     */
    public int getVersionNumberCount() {
        return this.versionNumbers.size();
    }
    
    public boolean isErrorDetected() {
        return errorDetected;
    }

    /**
     * @return the error string if {@link #isErrorDetected() isErrorDetected}
     * returns true
     */
    public String getErrorString() {
        return errorString;
    }
    
    @Override
    public int compareTo(Version other) {
        assert((! this.isErrorDetected()) && (! other.isErrorDetected()));
        
        int n1, n2;
        int len = Math.max(this.getVersionNumberCount(), 
                           other.getVersionNumberCount());
        for (int i = 0; i < len; i++) {
            n1 = this.getVersionNumber(i);
            n2 = other.getVersionNumber(i);
            if (n1 > n2) {
                return 1;
            }
            else if (n1 < n2){
                return -1;
            }
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return this.versionStr;
    }
}
