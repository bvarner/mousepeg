//=========================================================================
//
//  Part of PEG parser generator Mouse.
//
//  Copyright (C) 2010, 2019,2021
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
//     Created.
//    Version 1.6.1
//      Class MakeRuntime made public.
//    Version 2.0
//      Class MakeRuntime made static.
//    Version 2.1
//      Added 'SemAction'.
//    Version 2.3
//      'Defered' and 'SemAction' replaced by 'FuncVV'.
//      Added 'FuncVB'.
//
//=========================================================================

package mouse;

import mouse.utility.CommandArgs;
import java.io.*;

//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
//
//  MakeRuntime
//
//-------------------------------------------------------------------------
//
//  Re-package Mouse runtime support: write out Java source files
//  for runtime-support classes using specifed package name
//  instead of 'mouse.runtime'.
//
//  Invocation
//
//    java mouse.MakeRuntime <arguments>
//
//  The <arguments> are specified as options according to POSIX syntax:
//
//    -r <package>
//       Package name to be inserted in the generated files.
//
//    -D <directory>
//       Identifies target directory to receive the generated files.
//       Optional. If omitted, files are generated in current work directory.
//       The <directory> need not be a complete path, just enough to identify
//       the directory in current environment. The directory must exist.
//
//
//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH

public class MakeRuntime
{
  //=====================================================================
  //
  //  Data
  //
  //=====================================================================
  static String packName;   // Package name
  static String dirName;    // Output directory

  //=====================================================================
  //
  //  Invocation
  //
  //=====================================================================
  public static void main(String argv[])
    throws Exception
    {
      //---------------------------------------------------------------
      //  Parse arguments.
      //---------------------------------------------------------------
      CommandArgs cmd = new CommandArgs
             (argv,      // arguments to parse
              "",        // options without argument
              "Dr",      // options with argument
               0,0);     // no positional arguments

      if (cmd.nErrors()>0) return;

      //---------------------------------------------------------------
      //  Get options.
      //---------------------------------------------------------------
      packName = cmd.optArg('r');
      dirName  = cmd.optArg('D');

      if (packName==null)
      {
        System.out.println("Specify -r package name.");
        return;
      }

      if (dirName==null)
        dirName = "";
      else
        dirName = dirName + "/";

      //---------------------------------------------------------------
      //  Write files.
      //---------------------------------------------------------------
      write("CurrentRule.java");
      write("FuncVV.java");
      write("FuncVB.java");
      write("ParserBase.java");
      write("ParserMemo.java");
      write("ParserTest.java");
      write("Phrase.java");
      write("SemanticsBase.java");
      write("Source.java");
      write("SourceFile.java");
      write("SourceString.java");
    }

  //=====================================================================
  //
  //  Write out file 'name'
  //
  //=====================================================================
    static void write(String name)
      throws Exception
    {
      InputStream istream = MakeRuntime.class.getResourceAsStream("/mouse/rtsource/" + name);
      if (istream==null) throw new Exception("wow!");
      BufferedReader br = new BufferedReader(new InputStreamReader(istream));

      FileWriter out = new FileWriter(dirName + name);
      String strLine;

      while (true)
      {
        strLine = br.readLine();
        if (strLine==null) break;
        out.write(strLine.replace("mouse.runtime",packName) + "\n");
      }
      istream.close();
      out.close();
    }
}



