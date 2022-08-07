//=========================================================================
//
//  Part of PEG parser generator Mouse.
//
//  Copyright (C) 2017, 2020 by Roman R. Redziejowski (www.romanredz.se).
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//
//-------------------------------------------------------------------------
//
//  Change log
//    Version 1.9
//      Created.
//    Version 1.9.1
//      Removed hiding of removed #Start expression.
//      Added window size and position parameters to constructor.
//    Version 2.2
//      Removed automatic sorting of rows.
//      BitSet 'list' cotains indexes from 'Dual.index'.
//    Version 2.3
//      Removed serial version UID.
//
//=========================================================================

package mouse.explorer;

import javax.swing.*;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Vector;
import mouse.peg.Expr;
import mouse.utility.BitIter;

//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
//
//  GUI_List
//
//-------------------------------------------------------------------------
//
//  Base class for windows showing a list of expressions:
//  GUI_Grammar and GUI_First.
//
//  The window contains a single paragraph.
//  Each line of the paragraph represents one Expression.
//  The subclass fills the window by invoking the method 'show'
//  that takes as argument a list of Expressions.
//  The method is overloaded to accept the list in the form of array,
//  a Vector, a HashSet, or indexes in a BitSet.
//
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH

public abstract class GUI_List extends GUI
{
  //=====================================================================
  //
  //  Data
  //
  //=====================================================================
  //-------------------------------------------------------------------
  //  Copy of the paragraph, kept to preserve its type.
  //  Elements of 'paragraphs' in GUI are of type Paragraph.
  //-------------------------------------------------------------------
  Paragraph_Lines<Line_Expr> para;

  //=====================================================================
  //
  //  Constructor.
  //
  //=====================================================================
  protected GUI_List(int scale, int pos)
    { super(1,scale,pos); }

  //=====================================================================
  //
  //  Show list of expressions contained in 'list'.
  //
  //=====================================================================
  //-------------------------------------------------------------------
  //  'list' is an array.
  //-------------------------------------------------------------------
  void show(Expr[] list)
    {
      para = new Paragraph_Lines<Line_Expr>();

      for (Expr e: list)
        para.addLine(new Line_Expr(e));

      paragraphs[0] = para;
      write();
    }

  //-------------------------------------------------------------------
  //  'list' is a Vector.
  //-------------------------------------------------------------------
  void show(Vector<Expr> list)
    {
      Expr[] temp = new Expr[0];
      temp = list.toArray(temp);
      show(temp);
    }

  //-------------------------------------------------------------------
  //  'list' is a HashSet.
  //-------------------------------------------------------------------
  void show(HashSet<Expr> list)
    {
      Expr[] temp = new Expr[list.size()];
      int i = 0;
      for (Expr e: list)
      {
        temp[i] = e;
        i++;
      }
      show(temp);
    }

  //-------------------------------------------------------------------
  //  'list' consists of indexes in Dual.index.
  //-------------------------------------------------------------------
  void show(BitSet list)
    {
      Vector<Expr> temp = new Vector<Expr>();
      for (BitIter iter=new BitIter(list);iter.hasNext();)
        temp.add(Dual.index[iter.next()]);
      show(temp);
    }
}