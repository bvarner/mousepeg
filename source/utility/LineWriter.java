//=========================================================================
//
//  Part of PEG parser generator Mouse.
//
//  Copyright (C) 2009, 2010, 2012
//  by Roman R. Redziejowski (www.romanredz.se).
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
//    Version 1.2
//      License changed by the author to Apache v.2.
//    Version 1.3
//      Added file name to diagnostics.
//      Made 'indent' private.
//      Removed unused imports.
//    Version 1.5.1
//      (Steve Owens) Removed unused import.
//
//=========================================================================

package mouse.utility;

import java.io.FileWriter;
import java.io.IOException;

//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
//
//  LineWriter
//
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH

public class LineWriter
{
  private String fileName;
  private FileWriter out;
  private int indent = 0;

  private static String dashes =
    "//----------------------------------------------------------------------";

  private static String Dashes =
    "//==========================================================================";

  //-------------------------------------------------------------------
  //   Create LineWriter for output file 'fileName'.
  //-------------------------------------------------------------------
  public LineWriter(String fileName)
    {
      try
      { out = new FileWriter(fileName); }
      catch (IOException e)
      { throw new Error("Cannot open " + fileName); }
      this.fileName = fileName;
    }

  //-------------------------------------------------------------------
  //  Write line consisting of string 's',
  //  indented by 'indent' positions.
  //-------------------------------------------------------------------
  public void line(String s)
    {
      try
      {
        for (int i=0;i<indent;i++) out.write(" ");
        out.write(s);
        out.write("\n");
      }
      catch (IOException e)
      { throw new Error("Error writing " + fileName); }
    }

  //-------------------------------------------------------------------
  //  Write box containing string "s"
  //-------------------------------------------------------------------
  public void box(String s)
    {
      line(dashes.substring(0,71-indent));
      format(s,67-indent);
      line(dashes.substring(0,71-indent));
    }

  //-------------------------------------------------------------------
  //  Write medium box containing string "s"
  //-------------------------------------------------------------------
  public void Box(String s)
    {
      line(Dashes.substring(0,73-indent));
      format(s,69-indent);
      line(Dashes.substring(0,73-indent));
    }

  //-------------------------------------------------------------------
  //  Write large box containing string "s"
  //-------------------------------------------------------------------
  public void BOX(String s)
    {
      line(Dashes.substring(0,75-indent));
      line("//");
      format(s,71-indent);
      line("//");
      line(Dashes.substring(0,75-indent));
    }

  //-------------------------------------------------------------------
  //  Write string "s" as comment, splitting it at blanks
  //  into lines not exceeding "n" positions.
  //-------------------------------------------------------------------
  public void format(String s, int n)
    {
      String text = s;
      while (text.length()>0)
      {
        String rest = text;
        int i = text.indexOf('\n');
        if (i>=0)
        {
          rest = text.substring(0,i).trim();
          text = text.substring(i+1,text.length());
        }
        else
          text = "";

        String pfx = "//  ";
        int k = n;
        while (rest.length()>=k)
        {
          i = rest.lastIndexOf(' ',k);
          if (i>=0)
          {
            line(pfx + rest.substring(0,i));
            rest = rest.substring(i+1,rest.length());
          }

          else
          {
            line(pfx + rest.substring(0,k));
            rest = rest.substring(k,rest.length());
          }

          pfx = "//    ";
          k = n-2;
        }
        line(pfx + rest);
      }
    }

  //-------------------------------------------------------------------
  //  Increment / decrement indentation.
  //-------------------------------------------------------------------
  public void indent()
    { indent += 2; }

  public void undent()
    { indent -= 2; }

  //-------------------------------------------------------------------
  //  Close output.
  //-------------------------------------------------------------------
  public void close()
    {
      try
      { out.close(); }
      catch(IOException e)
      { throw new Error("Error closing " + fileName); }
    }
}
