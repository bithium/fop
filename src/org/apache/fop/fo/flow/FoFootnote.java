/*
 * $Id$
 * Copyright (C) 2001 The Apache Software Foundation. All rights reserved.
 * For details on use and redistribution please refer to the
 * LICENSE file included with these sources.
 *
 * @author <a href="mailto:pbwest@powerup.com.au">Peter B. West</a>
 */

package org.apache.fop.fo.flow;

// FOP
import org.apache.fop.fo.PropNames;
import org.apache.fop.fo.PropertySets;
import org.apache.fop.fo.FObjectNames;
import org.apache.fop.fo.FONode;
import org.apache.fop.fo.FOTree;
import org.apache.fop.fo.expr.PropertyException;
import org.apache.fop.xml.XMLEvent;
import org.apache.fop.xml.FoXMLEvent;
import org.apache.fop.apps.FOPException;
import org.apache.fop.datastructs.TreeException;
import org.apache.fop.datatypes.PropertyValue;
import org.apache.fop.datatypes.Ints;

import java.util.HashMap;
import java.util.BitSet;

/**
 * Implements the fo:footnote flow object.
 * <p>Footnote is an extremely messy flow object.  The only absolute
 * prohibition seem sto be that it may not be a descendant of another
 * fo:footnote.  <b>6.10.3 fo:footnote</b> <i>Constraints</i> states:
* <p><tt>"It is an error if the fo:footnote occurs as a
 * descendant of a flow that is not assigned to a region-body, or of an
 * fo:block-container that generates absolutely positioned areas. In either
 * case, the block-areas generated by the fo:footnote-body child of the
 * fo:footnote shall be returned to the parent of the fo:footnote and placed
 * in the area tree as though they were normal block-level areas."</tt>
 * <p>In other words, it's wrong, but you can do it anyway.  The end result
 * is that fo:footnote can appear almost anywhere.
 */
public class FoFootnote extends FONode {

    private static final String tag = "$Name$";
    private static final String revision = "$Revision$";

    /** Map of <tt>Integer</tt> indices of <i>sparsePropsSet</i> array.
        It is indexed by the FO index of the FO associated with a given
        position in the <i>sparsePropsSet</i> array. See
        {@link org.apache.fop.fo.FONode#sparsePropsSet FONode.sparsePropsSet}.
     */
    private static final HashMap sparsePropsMap;

    /** An <tt>int</tt> array of of the applicable property indices, in
        property index order. */
    private static final int[] sparseIndices;

    /** The number of applicable properties.  This is the size of the
        <i>sparsePropsSet</i> array. */
    private static final int numProps;

    static {
        // Collect the sets of properties that apply
        BitSet propsets = new BitSet();
        propsets.or(PropertySets.accessibilitySet);

        // Map these properties into sparsePropsSet
        // sparsePropsSet is a HashMap containing the indicies of the
        // sparsePropsSet array, indexed by the FO index of the FO slot
        // in sparsePropsSet.
        sparsePropsMap = new HashMap();
        numProps = propsets.cardinality();
        sparseIndices = new int[numProps];
        int propx = 0;
        for (int next = propsets.nextSetBit(0);
                next >= 0;
                next = propsets.nextSetBit(next + 1)) {
            sparseIndices[propx] = next;
            sparsePropsMap.put
                        (Ints.consts.get(next), Ints.consts.get(propx++));
        }
    }

    /**
     * Construct an fo:footnote node, and build the fo:footnote subtree.
     * <p>Content model for fo:footnote: (inline,footnote-body)
     * @param foTree the FO tree being built
     * @param parent the parent FONode of this node
     * @param event the <tt>FoXMLEvent</tt> that triggered the creation of
     * this node
     * @param stateFlags - passed down from the parent.  Includes the
     * attribute set information.
     */
    public FoFootnote
            (FOTree foTree, FONode parent, FoXMLEvent event, int stateFlags)
        throws TreeException, FOPException
    {
        super(foTree, FObjectNames.FOOTNOTE, parent, event,
                          stateFlags, sparsePropsMap, sparseIndices);
        if ((stateFlags & FONode.MC_FOOTNOTE) != 0)
            throw new FOPException
                    ("fo:footnote not allowed as child of fo:footnote.");
        FoXMLEvent ev;
        xmlevents = foTree.getXmlevents();
        try {
            // Look for the inline
            if ((ev = xmlevents.expectStartElement
                    (FObjectNames.INLINE, XMLEvent.DISCARD_W_SPACE))
                   == null)
                throw new FOPException("No inline in footnote.");
            new FoInline
                    (getFOTree(), this, ev, stateFlags | FONode.MC_FOOTNOTE);
            xmlevents.getEndElement(ev);

            // Look for the footnote-body
            if ((ev = xmlevents.expectStartElement
                    (FObjectNames.FOOTNOTE_BODY, XMLEvent.DISCARD_W_SPACE))
                   == null)
                throw new FOPException("No footnote-body in footnote.");
            new FoFootnoteBody
                    (getFOTree(), this, ev, stateFlags | FONode.MC_FOOTNOTE);
            xmlevents.getEndElement(ev);

            /*
        } catch (NoSuchElementException e) {
            throw new FOPException
                ("Unexpected EOF while processing " + nowProcessing + ".");
            */
        } catch(TreeException e) {
            throw new FOPException("TreeException: " + e.getMessage());
        } catch(PropertyException e) {
            throw new FOPException("PropertyException: " + e.getMessage());
        }

        makeSparsePropsSet();
    }

}
