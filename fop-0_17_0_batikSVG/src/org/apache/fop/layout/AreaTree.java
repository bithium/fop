/*-- $Id$ -- 

 ============================================================================
                   The Apache Software License, Version 1.1
 ============================================================================
 
    Copyright (C) 1999 The Apache Software Foundation. All rights reserved.
 
 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:
 
 1. Redistributions of  source code must  retain the above copyright  notice,
    this list of conditions and the following disclaimer.
 
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.
 
 3. The end-user documentation included with the redistribution, if any, must
    include  the following  acknowledgment:  "This product includes  software
    developed  by the  Apache Software Foundation  (http://www.apache.org/)."
    Alternately, this  acknowledgment may  appear in the software itself,  if
    and wherever such third-party acknowledgments normally appear.
 
 4. The names "Fop" and  "Apache Software Foundation"  must not be used to
    endorse  or promote  products derived  from this  software without  prior
    written permission. For written permission, please contact
    apache@apache.org.
 
 5. Products  derived from this software may not  be called "Apache", nor may
    "Apache" appear  in their name,  without prior written permission  of the
    Apache Software Foundation.
 
 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
 DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 This software  consists of voluntary contributions made  by many individuals
 on  behalf of the Apache Software  Foundation and was  originally created by
 James Tauber <jtauber@jtauber.com>. For more  information on the Apache 
 Software Foundation, please see <http://www.apache.org/>.
 
 */
package org.apache.fop.layout;

// FOP
import org.apache.fop.apps.FOPException;				   
import org.apache.fop.fo.flow.StaticContent;
import org.apache.fop.svg.*;
import org.apache.fop.render.Renderer;
import org.apache.fop.datatypes.IDReferences;
import org.apache.fop.extensions.ExtensionObj;

// Java
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;

public class AreaTree {

    /** object containing information on available fonts, including
	metrics */
    FontInfo fontInfo;
	
    /* list of all the pages */
    Vector pageList = new Vector();

    /** List of root extension objects */
    Vector rootExtensions = new Vector();
    

    IDReferences idReferences = new IDReferences();

    public void setFontInfo(FontInfo fontInfo) {
	this.fontInfo = fontInfo;
    }

    public FontInfo getFontInfo() {
	return this.fontInfo;
    }
    
    public void addPage(Page page) {
	this.pageList.addElement(page);
    }

    public Vector getPages() {
	return this.pageList;
    }

    public IDReferences getIDReferences()
    {
        return idReferences;
    }

    public void addExtension(ExtensionObj obj) 
    {
	rootExtensions.addElement(obj);
    }
    
    public Vector getExtensions() 
    {
	return rootExtensions;
    }
    

}