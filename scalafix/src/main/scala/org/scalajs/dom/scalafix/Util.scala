package org.scalajs.dom.scalafix

import scala.meta._
import scalafix.v1._

object Util {

  def parents(sym: Symbol)(implicit doc: SemanticDocument): List[SemanticType] =
    sym.info match {
      case Some(i) => parents(i.signature)
      case None    => Nil
    }

  def parents(sig: Signature)(implicit doc: SemanticDocument): List[SemanticType] =
    sig match {
      case x: ClassSignature => x.parents
      case _                 => Nil
    }

  // ===================================================================================================================

  def termName(pats: Iterable[Pat]): Option[Term.Name] =
    pats.iterator.flatMap(termName(_)).nextOption()

  def termName(pat: Pat): Option[Term.Name] =
    pat match {
      case x: Pat.Var => Some(x.name)
      case _          => None
    }

  def termName(t: Tree): Option[Term.Name] =
    t match {
      case d: Decl.Def => Some(d.name)
      case d: Decl.Val => termName(d.pats)
      case d: Decl.Var => termName(d.pats)
      case d: Defn.Def => Some(d.name)
      case d: Defn.Val => termName(d.pats)
      case d: Defn.Var => termName(d.pats)
      // case d: Defn.Object => termName(d.pats) // ?
      case _ => None
    }

  // ===================================================================================================================

  def typeSymbol(t: SemanticType): Symbol =
    t match {
      case x: TypeRef          => x.symbol
      case x: SingleType       => x.symbol
      case x: ThisType         => x.symbol
      case x: SuperType        => x.symbol
      // case x: ConstantType     => xxx  // (constant) =>
      // case x: IntersectionType => xxx  // (types) =>
      // case x: UnionType        => xxx  // (types) =>
      // case x: WithType         => xxx  // (types) =>
      // case x: StructuralType   => xxx  // (tpe, declarations) =>
      // case x: AnnotatedType    => xxx  // (annotations, tpe) =>
      // case x: ExistentialType  => xxx  // (tpe, declarations) =>
      // case x: UniversalType    => xxx  // (typeParameters, tpe) =>
      // case x: ByNameType       => xxx  // (tpe) =>
      // case x: RepeatedType     => xxx  // (tpe) =>
      // case NoType              => Symbol.None
      case _              => Symbol.None
    }


}