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
//      Created
//    Version 2.2
//      Class made public.
//    Version 2.3
//      Correction: 'asString' in 'asString' replaced by 'toShort'.
//
//=========================================================================

package mouse.explorer;

import mouse.peg.Expr;

//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
//
//  Class TermPair
//
//-------------------------------------------------------------------------
//
//  A pair of terminals.
//  Used to list non-disjoint terminals.
//
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH

public class TermPair
{
  public Expr term1;
  public Expr term2;

  //------------------------------------------------------------------
  //  Constructor.
  //------------------------------------------------------------------
  public TermPair(Expr t1, Expr t2)
    {
      term1 = t1;
      term2 = t2;
    }

  //------------------------------------------------------------------
  //  Present the pair as a String.
  //------------------------------------------------------------------
  public String asString()
    { return term1.toShort() + "  <==>  " + term2.toShort(); }
}

