//=========================================================================
//
//  This file was generated by Mouse 2.3 at 2021-04-27 15:25:21 GMT
//  from grammar 'C:\Users\Giraf\Mouse\mouse\peg\grammar.peg'.
//
//=========================================================================

package mouse.peg;

import mouse.runtime.Source;
import mouse.runtime.FuncVV;

public class Parser extends mouse.runtime.ParserBase
{
  Semantics sem;

  //=======================================================================
  //
  //  Initialization
  //
  //=======================================================================
  //-------------------------------------------------------------------
  //  Constructor
  //-------------------------------------------------------------------
  public Parser()
    {
      sem = new Semantics();
      sem.rule = this;
      super.sem = sem;
    }
  
  //-------------------------------------------------------------------
  //  Run the parser
  //-------------------------------------------------------------------
  public boolean parse(Source src)
    {
      super.init(src);
      sem.init();
      boolean result = Grammar();
      closeParser(result);
      return result;
    }
  
  //-------------------------------------------------------------------
  //  Get semantics
  //-------------------------------------------------------------------
  public Semantics semantics()
    { return sem; }
  
  //=======================================================================
  //
  //  Parsing procedures
  //
  //=======================================================================
  //=====================================================================
  //  Grammar = Space (Rule / Skip)*+ EOT {Grammar} ;
  //=====================================================================
  boolean Grammar()
    {
      begin("Grammar");
      Space();
      while (!EOT())
        if (!Grammar_2()) return reject();
      sem.Grammar();
      return accept();
    }
  
  //-------------------------------------------------------------------
  //  Grammar_2 = Rule / Skip
  //-------------------------------------------------------------------
  boolean Grammar_2()
    {
      begin("Grammar_2");
      if (Rule()) return acceptInner();
      if (Skip()) return acceptInner();
      return rejectInner();
    }
  
  //=====================================================================
  //  Rule = Name EQUAL RuleRhs DiagName? SEMI {Rule} ~{Error} ;
  //=====================================================================
  boolean Rule()
    {
      begin("Rule");
      if (Rule_0())
      { sem.Rule(); return accept(); }
      sem.Error();
      return reject();
    }
  
  //-------------------------------------------------------------------
  //  Rule_0 = Name EQUAL RuleRhs DiagName? SEMI
  //-------------------------------------------------------------------
  boolean Rule_0()
    {
      begin("Rule_0");
      if (!Name()) return rejectInner();
      if (!EQUAL()) return rejectInner();
      if (!RuleRhs()) return rejectInner();
      DiagName();
      if (!SEMI()) return rejectInner();
      return acceptInner();
    }
  
  //=====================================================================
  //  Skip = SEMI / _++ (SEMI / EOT) ;
  //=====================================================================
  boolean Skip()
    {
      begin("Skip");
      if (SEMI()) return accept();
      if (Skip_0()) return accept();
      return reject();
    }
  
  //-------------------------------------------------------------------
  //  Skip_0 = _++ (SEMI / EOT)
  //-------------------------------------------------------------------
  boolean Skip_0()
    {
      begin("Skip_0");
      if (Skip_2()) return rejectInner();
      do if (!next()) return rejectInner();
        while (!Skip_2());
      return acceptInner();
    }
  
  //-------------------------------------------------------------------
  //  Skip_2 = SEMI / EOT
  //-------------------------------------------------------------------
  boolean Skip_2()
    {
      begin("Skip_2");
      if (SEMI()) return acceptInner();
      if (EOT()) return acceptInner();
      return rejectInner();
    }
  
  //=====================================================================
  //  RuleRhs = Sequence Actions (SLASH Sequence Actions)* {RuleRhs}
  //    <right-hand side> ;
  //=====================================================================
  boolean RuleRhs()
    {
      begin("RuleRhs","right-hand side");
      if (!Sequence()) return reject();
      Actions();
      while (RuleRhs_2());
      sem.RuleRhs();
      return accept();
    }
  
  //-------------------------------------------------------------------
  //  RuleRhs_2 = SLASH Sequence Actions
  //-------------------------------------------------------------------
  boolean RuleRhs_2()
    {
      begin("RuleRhs_2");
      if (!SLASH()) return rejectInner();
      if (!Sequence()) return rejectInner();
      Actions();
      return acceptInner();
    }
  
  //=====================================================================
  //  Choice = Sequence (SLASH Sequence)* {Choice} ;
  //=====================================================================
  boolean Choice()
    {
      begin("Choice");
      if (!Sequence()) return reject();
      while (Choice_2());
      sem.Choice();
      return accept();
    }
  
  //-------------------------------------------------------------------
  //  Choice_2 = SLASH Sequence
  //-------------------------------------------------------------------
  boolean Choice_2()
    {
      begin("Choice_2");
      if (!SLASH()) return rejectInner();
      if (!Sequence()) return rejectInner();
      return acceptInner();
    }
  
  //=====================================================================
  //  Sequence = Prefixed+ {Sequence} ;
  //=====================================================================
  boolean Sequence()
    {
      begin("Sequence");
      if (!Prefixed()) return reject();
      while (Prefixed());
      sem.Sequence();
      return accept();
    }
  
  //=====================================================================
  //  Prefixed = (AND / NOT) Suffixed {Prefix} / Suffixed {Pass} ;
  //=====================================================================
  boolean Prefixed()
    {
      begin("Prefixed");
      if (Prefixed_0())
      { sem.Prefix(); return accept(); }
      if (Suffixed())
      { sem.Pass(); return accept(); }
      return reject();
    }
  
  //-------------------------------------------------------------------
  //  Prefixed_0 = (AND / NOT) Suffixed
  //-------------------------------------------------------------------
  boolean Prefixed_0()
    {
      begin("Prefixed_0");
      if (!AND()
       && !NOT()
         ) return rejectInner();
      if (!Suffixed()) return rejectInner();
      return acceptInner();
    }
  
  //=====================================================================
  //  Suffixed = Primary (STARPLUS / PLUSPLUS / IS / ISNOT) Primary
  //    {Infix} / Primary (QUERY / STAR / PLUS) {Suffix} / Primary {Pass}
  //    ;
  //=====================================================================
  boolean Suffixed()
    {
      begin("Suffixed");
      if (Suffixed_0())
      { sem.Infix(); return accept(); }
      if (Suffixed_2())
      { sem.Suffix(); return accept(); }
      if (Primary())
      { sem.Pass(); return accept(); }
      return reject();
    }
  
  //-------------------------------------------------------------------
  //  Suffixed_0 = Primary (STARPLUS / PLUSPLUS / IS / ISNOT) Primary
  //-------------------------------------------------------------------
  boolean Suffixed_0()
    {
      begin("Suffixed_0");
      if (!Primary()) return rejectInner();
      if (!STARPLUS()
       && !PLUSPLUS()
       && !IS()
       && !ISNOT()
         ) return rejectInner();
      if (!Primary()) return rejectInner();
      return acceptInner();
    }
  
  //-------------------------------------------------------------------
  //  Suffixed_2 = Primary (QUERY / STAR / PLUS)
  //-------------------------------------------------------------------
  boolean Suffixed_2()
    {
      begin("Suffixed_2");
      if (!Primary()) return rejectInner();
      if (!QUERY()
       && !STAR()
       && !PLUS()
         ) return rejectInner();
      return acceptInner();
    }
  
  //=====================================================================
  //  Primary = Name {Resolve} / LPAREN Choice RPAREN {Pass2} / ANY {Any}
  //    / StringLit {Pass} / Range {Pass} / CharClass {Pass} ;
  //=====================================================================
  boolean Primary()
    {
      begin("Primary");
      if (Name())
      { sem.Resolve(); return accept(); }
      if (Primary_0())
      { sem.Pass2(); return accept(); }
      if (ANY())
      { sem.Any(); return accept(); }
      if (StringLit())
      { sem.Pass(); return accept(); }
      if (Range())
      { sem.Pass(); return accept(); }
      if (CharClass())
      { sem.Pass(); return accept(); }
      return reject();
    }
  
  //-------------------------------------------------------------------
  //  Primary_0 = LPAREN Choice RPAREN
  //-------------------------------------------------------------------
  boolean Primary_0()
    {
      begin("Primary_0");
      if (!LPAREN()) return rejectInner();
      if (!Choice()) return rejectInner();
      if (!RPAREN()) return rejectInner();
      return acceptInner();
    }
  
  //=====================================================================
  //  Actions = OnSucc OnFail {Actions} ;
  //=====================================================================
  boolean Actions()
    {
      begin("Actions");
      OnSucc();
      OnFail();
      sem.Actions();
      return accept();
    }
  
  //=====================================================================
  //  OnSucc = (LWING AND? Name? RWING)? {OnSucc} ;
  //=====================================================================
  boolean OnSucc()
    {
      begin("OnSucc");
      OnSucc_1();
      sem.OnSucc();
      return accept();
    }
  
  //-------------------------------------------------------------------
  //  OnSucc_1 = LWING AND? Name? RWING
  //-------------------------------------------------------------------
  boolean OnSucc_1()
    {
      begin("OnSucc_1");
      if (!LWING()) return rejectInner();
      AND();
      Name();
      if (!RWING()) return rejectInner();
      return acceptInner();
    }
  
  //=====================================================================
  //  OnFail = (TILDA LWING Name? RWING)? {OnFail} ;
  //=====================================================================
  boolean OnFail()
    {
      begin("OnFail");
      OnFail_1();
      sem.OnFail();
      return accept();
    }
  
  //-------------------------------------------------------------------
  //  OnFail_1 = TILDA LWING Name? RWING
  //-------------------------------------------------------------------
  boolean OnFail_1()
    {
      begin("OnFail_1");
      if (!TILDA()) return rejectInner();
      if (!LWING()) return rejectInner();
      Name();
      if (!RWING()) return rejectInner();
      return acceptInner();
    }
  
  //=====================================================================
  //  Name = Letter (Letter / Digit)* Space {Name} ;
  //=====================================================================
  boolean Name()
    {
      begin("Name");
      if (!Letter()) return reject();
      while (Name_2());
      Space();
      sem.Name();
      return accept();
    }
  
  //-------------------------------------------------------------------
  //  Name_2 = Letter / Digit
  //-------------------------------------------------------------------
  boolean Name_2()
    {
      begin("Name_2");
      if (Letter()) return acceptInner();
      if (Digit()) return acceptInner();
      return rejectInner();
    }
  
  //=====================================================================
  //  DiagName = "<" Char++ ">" Space {DiagName} ;
  //=====================================================================
  boolean DiagName()
    {
      begin("DiagName");
      if (!next('<')) return reject();
      if (next('>')) return reject();
      do if (!Char()) return reject();
        while (!next('>'));
      Space();
      sem.DiagName();
      return accept();
    }
  
  //=====================================================================
  //  StringLit = ["] Char++ ["] Space {StringLit} ;
  //=====================================================================
  boolean StringLit()
    {
      begin("StringLit");
      if (!next('"')) return reject();
      if (next('"')) return reject();
      do if (!Char()) return reject();
        while (!next('"'));
      Space();
      sem.StringLit();
      return accept();
    }
  
  //=====================================================================
  //  CharClass = ("[" / "^[") Char++ "]" Space {CharClass} ;
  //=====================================================================
  boolean CharClass()
    {
      begin("CharClass");
      if (!next('[')
       && !next("^[")
         ) return reject();
      if (next(']')) return reject();
      do if (!Char()) return reject();
        while (!next(']'));
      Space();
      sem.CharClass();
      return accept();
    }
  
  //=====================================================================
  //  Range = "[" Char "-" Char "]" Space {Range} ;
  //=====================================================================
  boolean Range()
    {
      begin("Range");
      if (!next('[')) return reject();
      if (!Char()) return reject();
      if (!next('-')) return reject();
      if (!Char()) return reject();
      if (!next(']')) return reject();
      Space();
      sem.Range();
      return accept();
    }
  
  //=====================================================================
  //  Char = Escape {Pass} / ^[\r\n\] {Char} ;
  //=====================================================================
  boolean Char()
    {
      begin("Char");
      if (Escape())
      { sem.Pass(); return accept(); }
      if (nextNotIn("\r\n\\"))
      { sem.Char(); return accept(); }
      return reject();
    }
  
  //=====================================================================
  //  Escape = "\ u" HexDigit HexDigit HexDigit HexDigit {Unicode} / "\t"
  //    {Tab} / "\n" {Newline} / "\r" {CarRet} / !"\ u" "\" _ {Escape} ;
  //=====================================================================
  boolean Escape()
    {
      begin("Escape");
      if (Escape_0())
      { sem.Unicode(); return accept(); }
      if (next("\\t"))
      { sem.Tab(); return accept(); }
      if (next("\\n"))
      { sem.Newline(); return accept(); }
      if (next("\\r"))
      { sem.CarRet(); return accept(); }
      if (Escape_5())
      { sem.Escape(); return accept(); }
      return reject();
    }
  
  //-------------------------------------------------------------------
  //  Escape_0 = "\ u" HexDigit HexDigit HexDigit HexDigit
  //-------------------------------------------------------------------
  boolean Escape_0()
    {
      begin("Escape_0");
      if (!next("\\u")) return rejectInner();
      if (!HexDigit()) return rejectInner();
      if (!HexDigit()) return rejectInner();
      if (!HexDigit()) return rejectInner();
      if (!HexDigit()) return rejectInner();
      return acceptInner();
    }
  
  //-------------------------------------------------------------------
  //  Escape_5 = !"\ u" "\" _
  //-------------------------------------------------------------------
  boolean Escape_5()
    {
      begin("Escape_5");
      if (!aheadNot("\\u")) return rejectInner();
      if (!next('\\')) return rejectInner();
      if (!next()) return rejectInner();
      return acceptInner();
    }
  
  //=====================================================================
  //  Letter = [a-z] / [A-Z] ;
  //=====================================================================
  boolean Letter()
    {
      begin("Letter");
      if (nextIn('a','z')) return accept();
      if (nextIn('A','Z')) return accept();
      return reject();
    }
  
  //=====================================================================
  //  Digit = [0-9] ;
  //=====================================================================
  boolean Digit()
    {
      begin("Digit");
      if (!nextIn('0','9')) return reject();
      return accept();
    }
  
  //=====================================================================
  //  HexDigit = [0-9] / [a-f] / [A-F] ;
  //=====================================================================
  boolean HexDigit()
    {
      begin("HexDigit");
      if (nextIn('0','9')) return accept();
      if (nextIn('a','f')) return accept();
      if (nextIn('A','F')) return accept();
      return reject();
    }
  
  //=====================================================================
  //  AND = [&] Space <&> ;
  //=====================================================================
  boolean AND()
    {
      begin("AND","&");
      if (!next('&')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  NOT = [!] Space <!> ;
  //=====================================================================
  boolean NOT()
    {
      begin("NOT","!");
      if (!next('!')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  QUERY = [?] Space <?> ;
  //=====================================================================
  boolean QUERY()
    {
      begin("QUERY","?");
      if (!next('?')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  STAR = [*] ![+] Space <*> ;
  //=====================================================================
  boolean STAR()
    {
      begin("STAR","*");
      if (!next('*')) return reject();
      if (!aheadNot('+')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  PLUS = [+] ![+] Space <+> ;
  //=====================================================================
  boolean PLUS()
    {
      begin("PLUS","+");
      if (!next('+')) return reject();
      if (!aheadNot('+')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  STARPLUS = "*+" Space <*+> ;
  //=====================================================================
  boolean STARPLUS()
    {
      begin("STARPLUS","*+");
      if (!next("*+")) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  PLUSPLUS = "++" Space <++> ;
  //=====================================================================
  boolean PLUSPLUS()
    {
      begin("PLUSPLUS","++");
      if (!next("++")) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  IS = [:] ![!] Space <:> ;
  //=====================================================================
  boolean IS()
    {
      begin("IS",":");
      if (!next(':')) return reject();
      if (!aheadNot('!')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  ISNOT = ":!" Space <:!> ;
  //=====================================================================
  boolean ISNOT()
    {
      begin("ISNOT",":!");
      if (!next(":!")) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  EQUAL = [=] Space <=> ;
  //=====================================================================
  boolean EQUAL()
    {
      begin("EQUAL","=");
      if (!next('=')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  LPAREN = [(] Space <(> ;
  //=====================================================================
  boolean LPAREN()
    {
      begin("LPAREN","(");
      if (!next('(')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  RPAREN = [)] Space <)> ;
  //=====================================================================
  boolean RPAREN()
    {
      begin("RPAREN",")");
      if (!next(')')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  LWING = [{] Space <{> ;
  //=====================================================================
  boolean LWING()
    {
      begin("LWING","{");
      if (!next('{')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  RWING = [}] Space <}> ;
  //=====================================================================
  boolean RWING()
    {
      begin("RWING","}");
      if (!next('}')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  SEMI = [;] Space <;> ;
  //=====================================================================
  boolean SEMI()
    {
      begin("SEMI",";");
      if (!next(';')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  SLASH = [/] Space </> ;
  //=====================================================================
  boolean SLASH()
    {
      begin("SLASH","/");
      if (!next('/')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  TILDA = [~] Space <~> ;
  //=====================================================================
  boolean TILDA()
    {
      begin("TILDA","~");
      if (!next('~')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  ANY = [_] Space <_> ;
  //=====================================================================
  boolean ANY()
    {
      begin("ANY","_");
      if (!next('_')) return reject();
      Space();
      return accept();
    }
  
  //=====================================================================
  //  Space = ([ \r\n\t] / Comment)* {Space} ;
  //=====================================================================
  boolean Space()
    {
      begin("Space");
      while (Space_1());
      sem.Space();
      return accept();
    }
  
  //-------------------------------------------------------------------
  //  Space_1 = [ \r\n\t] / Comment
  //-------------------------------------------------------------------
  boolean Space_1()
    {
      begin("Space_1");
      if (nextIn(" \r\n\t")) return acceptInner();
      if (Comment()) return acceptInner();
      return rejectInner();
    }
  
  //=====================================================================
  //  Comment = "//" _*+ EOL ;
  //=====================================================================
  boolean Comment()
    {
      begin("Comment");
      if (!next("//")) return reject();
      while (!EOL())
        if (!next()) return reject();
      return accept();
    }
  
  //=====================================================================
  //  EOL = [\r]? [\n] / !_ <end of line> ;
  //=====================================================================
  boolean EOL()
    {
      begin("EOL","end of line");
      if (EOL_0()) return accept();
      if (aheadNot()) return accept();
      return reject();
    }
  
  //-------------------------------------------------------------------
  //  EOL_0 = [\r]? [\n]
  //-------------------------------------------------------------------
  boolean EOL_0()
    {
      begin("EOL_0");
      next('\r');
      if (!next('\n')) return rejectInner();
      return acceptInner();
    }
  
  //=====================================================================
  //  EOT = !_ <end of text> ;
  //=====================================================================
  boolean EOT()
    {
      begin("EOT","end of text");
      if (!aheadNot()) return reject();
      return accept();
    }
  
}
