package io.github.potjerodekool.nabuidea.language

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import io.github.potjerodekool.nabu.NabuLexer
import io.github.potjerodekool.nabu.NabuParser
import io.github.potjerodekool.nabuidea.NabuLanguage
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementKind
import io.github.potjerodekool.nabuidea.language.psi.NabuFile
import io.github.potjerodekool.nabuidea.language.psi.NabuAdditionalBound
import io.github.potjerodekool.nabuidea.language.psi.NabuAdditiveExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuAmbiguousName
import io.github.potjerodekool.nabuidea.language.psi.NabuAndExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuAnnotation
import io.github.potjerodekool.nabuidea.language.psi.NabuAnnotationInterfaceBody
import io.github.potjerodekool.nabuidea.language.psi.NabuAnnotationInterfaceDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuAnnotationInterfaceElementDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuAnnotationInterfaceMemberDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuArrayType
import io.github.potjerodekool.nabuidea.language.psi.NabuAssignmentExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuClassBody
import io.github.potjerodekool.nabuidea.language.psi.NabuClassBodyDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuClassDeclarationWrapper
import io.github.potjerodekool.nabuidea.language.psi.NabuClassExtends
import io.github.potjerodekool.nabuidea.language.psi.NabuClassImplements
import io.github.potjerodekool.nabuidea.language.psi.NabuClassLiteral
import io.github.potjerodekool.nabuidea.language.psi.NabuClassMemberDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuClassOrInterfaceType
import io.github.potjerodekool.nabuidea.language.psi.NabuClassPermits
import io.github.potjerodekool.nabuidea.language.psi.NabuClassType
import io.github.potjerodekool.nabuidea.language.psi.NabuCoit
import io.github.potjerodekool.nabuidea.language.psi.NabuCompactConstructorDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuCompilationUnitWrapper
import io.github.potjerodekool.nabuidea.language.psi.NabuConditionalAndExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuConditionalExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuConditionalOrExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuConstantDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuConstructorBody
import io.github.potjerodekool.nabuidea.language.psi.NabuConstructorDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuConstructorDeclarator
import io.github.potjerodekool.nabuidea.language.psi.NabuContextualKeyword
import io.github.potjerodekool.nabuidea.language.psi.NabuContextualKeywordMinusForTypeIdentifier
import io.github.potjerodekool.nabuidea.language.psi.NabuContextualKeywordMinusForUnqualifiedFunctionIdentifier
import io.github.potjerodekool.nabuidea.language.psi.NabuDefaultValue
import io.github.potjerodekool.nabuidea.language.psi.NabuDims
import io.github.potjerodekool.nabuidea.language.psi.NabuEnumBody
import io.github.potjerodekool.nabuidea.language.psi.NabuEnumBodyDeclarations
import io.github.potjerodekool.nabuidea.language.psi.NabuEnumConstant
import io.github.potjerodekool.nabuidea.language.psi.NabuEnumConstantList
import io.github.potjerodekool.nabuidea.language.psi.NabuEqualityExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuExceptionType
import io.github.potjerodekool.nabuidea.language.psi.NabuExceptionTypeList
import io.github.potjerodekool.nabuidea.language.psi.NabuExclusiveOrExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuExplicitConstructorInvocation
import io.github.potjerodekool.nabuidea.language.psi.NabuExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuExpressionName
import io.github.potjerodekool.nabuidea.language.psi.NabuFieldDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuFloatingPointType
import io.github.potjerodekool.nabuidea.language.psi.NabuFormalParameter
import io.github.potjerodekool.nabuidea.language.psi.NabuFormalParameterList
import io.github.potjerodekool.nabuidea.language.psi.NabuFunctionBody
import io.github.potjerodekool.nabuidea.language.psi.NabuFunctionDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuFunctionDeclarator
import io.github.potjerodekool.nabuidea.language.psi.NabuFunctionHeader
import io.github.potjerodekool.nabuidea.language.psi.NabuFunctionName
import io.github.potjerodekool.nabuidea.language.psi.NabuIdentifier
import io.github.potjerodekool.nabuidea.language.psi.NabuImportDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuInclusiveOrExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuInstanceInitializer
import io.github.potjerodekool.nabuidea.language.psi.NabuIntegralType
import io.github.potjerodekool.nabuidea.language.psi.NabuInterfaceBody
import io.github.potjerodekool.nabuidea.language.psi.NabuInterfaceDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuInterfaceExtends
import io.github.potjerodekool.nabuidea.language.psi.NabuInterfaceFunctionDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuInterfaceMemberDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuInterfacePermits
import io.github.potjerodekool.nabuidea.language.psi.NabuInterfaceType
import io.github.potjerodekool.nabuidea.language.psi.NabuInterfaceTypeList
import io.github.potjerodekool.nabuidea.language.psi.NabuLiteral
import io.github.potjerodekool.nabuidea.language.psi.NabuModifier
import io.github.potjerodekool.nabuidea.language.psi.NabuModuleDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuModuleDirective
import io.github.potjerodekool.nabuidea.language.psi.NabuModuleName
import io.github.potjerodekool.nabuidea.language.psi.NabuMultiplicativeExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuNormalAnnotation
import io.github.potjerodekool.nabuidea.language.psi.NabuClassDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuNormalInterfaceDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuNumericType
import io.github.potjerodekool.nabuidea.language.psi.NabuCompilationUnit
import io.github.potjerodekool.nabuidea.language.psi.NabuPackageDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuPackageModifier
import io.github.potjerodekool.nabuidea.language.psi.NabuPackageName
import io.github.potjerodekool.nabuidea.language.psi.NabuPackageOrTypeName
import io.github.potjerodekool.nabuidea.language.psi.NabuPostfixExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuPrimary
import io.github.potjerodekool.nabuidea.language.psi.NabuPrimaryNoNewArray
import io.github.potjerodekool.nabuidea.language.psi.NabuPrimitiveType
import io.github.potjerodekool.nabuidea.language.psi.NabuReceiverParameter
import io.github.potjerodekool.nabuidea.language.psi.NabuRecordBody
import io.github.potjerodekool.nabuidea.language.psi.NabuRecordBodyDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuRecordComponent
import io.github.potjerodekool.nabuidea.language.psi.NabuRecordComponentList
import io.github.potjerodekool.nabuidea.language.psi.NabuRecordHeader
import io.github.potjerodekool.nabuidea.language.psi.NabuReferenceType
import io.github.potjerodekool.nabuidea.language.psi.NabuRelationalExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuRequiresModifier
import io.github.potjerodekool.nabuidea.language.psi.NabuResult
import io.github.potjerodekool.nabuidea.language.psi.NabuShiftExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuSimpleTypeName
import io.github.potjerodekool.nabuidea.language.psi.NabuSingleStaticImportDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuSingleTypeImportDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuStandardElement
import io.github.potjerodekool.nabuidea.language.psi.NabuStart
import io.github.potjerodekool.nabuidea.language.psi.NabuStaticImportOnDemandDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuStaticInitializer
import io.github.potjerodekool.nabuidea.language.psi.NabuThrowsT
import io.github.potjerodekool.nabuidea.language.psi.NabuTopLevelClassOrInterfaceDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeArgument
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeArgumentList
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeArguments
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeBound
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeIdentifier
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeImportOnDemandDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeName
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeParameter
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeParameterList
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeParameterModifier
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeParameters
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeVariable
import io.github.potjerodekool.nabuidea.language.psi.NabuUCOIT
import io.github.potjerodekool.nabuidea.language.psi.NabuUnannArrayType
import io.github.potjerodekool.nabuidea.language.psi.NabuUnannClassOrInterfaceType
import io.github.potjerodekool.nabuidea.language.psi.NabuUnannClassType
import io.github.potjerodekool.nabuidea.language.psi.NabuUnannInterfaceType
import io.github.potjerodekool.nabuidea.language.psi.NabuUnannPrimitiveType
import io.github.potjerodekool.nabuidea.language.psi.NabuUnannReferenceType
import io.github.potjerodekool.nabuidea.language.psi.NabuUnannType
import io.github.potjerodekool.nabuidea.language.psi.NabuUnannTypeVariable
import io.github.potjerodekool.nabuidea.language.psi.NabuUnaryExpression
import io.github.potjerodekool.nabuidea.language.psi.NabuUnaryExpressionNotPlusMinus
import io.github.potjerodekool.nabuidea.language.psi.NabuUnqualifiedFunctionIdentifier
import io.github.potjerodekool.nabuidea.language.psi.NabuVariableArityParameter
import io.github.potjerodekool.nabuidea.language.psi.NabuVariableArityRecordComponent
import io.github.potjerodekool.nabuidea.language.psi.NabuVariableDeclarator
import io.github.potjerodekool.nabuidea.language.psi.NabuVariableDeclaratorId
import io.github.potjerodekool.nabuidea.language.psi.NabuVariableDeclaratorList
import io.github.potjerodekool.nabuidea.language.psi.NabuVariableInitializer
import io.github.potjerodekool.nabuidea.language.psi.NabuWildcard
import io.github.potjerodekool.nabuidea.language.psi.NabuWildcardBounds
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.tree.ParseTree

class NabuParserDefinition : ParserDefinition {

    val FILE: IFileElementType = IFileElementType(NabuLanguage)

    val tokenElementTypes: List<TokenIElementType>
    val tokenElementTypeMap: Map<String, TokenIElementType>
    val whiteSpaceTokenSet: TokenSet

    init {
        tokenElementTypes = PSIElementTypeFactory.getTokenIElementTypes(NabuLanguage)

        tokenElementTypeMap = tokenElementTypes.associateBy { removeSingleQuotes(it.debugName) }

        val whiteSpaceToken = tokenElementTypeMap["WS"]

        whiteSpaceTokenSet = if (whiteSpaceToken != null)
            TokenSet.create(whiteSpaceToken)
        else TokenSet.WHITE_SPACE
    }

    private fun removeSingleQuotes(key: String): String {
        if (key.length > 1 && key[0] == '\'' && key[key.length - 1] == '\'') {
            return key.substring(1, key.length - 1)
        } else {
            return key
        }
    }

    override fun createLexer(project: Project?): Lexer {
        val lexer = NabuLexer(null)
        return ANTLRLexerAdaptor(NabuLanguage, lexer)
    }

    override fun createParser(project: Project?): PsiParser {
        val parser = NabuParser(null)

        return object : ANTLRParserAdaptor(NabuLanguage, parser) {
            override fun parse(parser: Parser?, root: IElementType?): ParseTree? {
                return (parser as NabuParser).start_()
            }
        }
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun getCommentTokens(): TokenSet {
        //TODO return NabuTokenSets.COMMENTS
        return TokenSet.EMPTY
    }

    override fun getWhitespaceTokens(): TokenSet {
        return whiteSpaceTokenSet
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createElement(node: ASTNode?): PsiElement {
        val elementType = node!!.elementType

        if (elementType is RuleIElementType) {
            val index = elementType.ruleIndex

            return when(index) {
                NabuParser.RULE_start_ -> NabuStart(node)
                NabuParser.RULE_identifier -> NabuIdentifier(node)
                NabuParser.RULE_typeIdentifier -> NabuTypeIdentifier(node)
                NabuParser.RULE_unqualifiedFunctionIdentifier -> NabuUnqualifiedFunctionIdentifier(node)
                NabuParser.RULE_contextualKeyword -> NabuContextualKeyword(node)
                NabuParser.RULE_contextualKeywordMinusForTypeIdentifier -> NabuContextualKeywordMinusForTypeIdentifier(node)
                NabuParser.RULE_contextualKeywordMinusForUnqualifiedFunctionIdentifier -> NabuContextualKeywordMinusForUnqualifiedFunctionIdentifier(node)
                NabuParser.RULE_literal -> NabuLiteral(node)
                NabuParser.RULE_primitiveType -> NabuPrimitiveType(node)
                NabuParser.RULE_numericType -> NabuNumericType(node)
                NabuParser.RULE_integralType -> NabuIntegralType(node)
                NabuParser.RULE_floatingPointType -> NabuFloatingPointType(node)
                NabuParser.RULE_referenceType -> NabuReferenceType(node)
                NabuParser.RULE_coit -> NabuCoit(node)
                NabuParser.RULE_classOrInterfaceType -> NabuClassOrInterfaceType(node)
                NabuParser.RULE_classType -> NabuClassType(node)
                NabuParser.RULE_interfaceType -> NabuInterfaceType(node)
                NabuParser.RULE_typeVariable -> NabuTypeVariable(node)
                NabuParser.RULE_arrayType -> NabuArrayType(node)
                NabuParser.RULE_dims -> NabuDims(node)
                NabuParser.RULE_typeParameter -> NabuTypeParameter(node)
                NabuParser.RULE_typeParameterModifier -> NabuTypeParameterModifier(node)
                NabuParser.RULE_typeBound -> NabuTypeBound(node)
                NabuParser.RULE_additionalBound -> NabuAdditionalBound(node)
                NabuParser.RULE_typeArguments -> NabuTypeArguments(node)
                NabuParser.RULE_typeArgumentList -> NabuTypeArgumentList(node)
                NabuParser.RULE_typeArgument -> NabuTypeArgument(node)
                NabuParser.RULE_wildcard -> NabuWildcard(node)
                NabuParser.RULE_wildcardBounds -> NabuWildcardBounds(node)
                NabuParser.RULE_moduleName -> NabuModuleName(node)
                NabuParser.RULE_packageName -> NabuPackageName(node)
                NabuParser.RULE_typeName -> NabuTypeName(node)
                NabuParser.RULE_packageOrTypeName -> NabuPackageOrTypeName(node)
                NabuParser.RULE_expressionName -> NabuExpressionName(node)
                NabuParser.RULE_functionName -> NabuFunctionName(node)
                NabuParser.RULE_ambiguousName -> NabuAmbiguousName(node)
                NabuParser.RULE_compilationUnit -> NabuCompilationUnitWrapper(node)
                NabuParser.RULE_ordinaryCompilationUnit -> NabuCompilationUnit(node, false)
                NabuParser.RULE_modularCompilationUnit -> NabuCompilationUnit(node, true)
                NabuParser.RULE_packageDeclaration -> NabuPackageDeclaration(node)
                NabuParser.RULE_packageModifier -> NabuPackageModifier(node)
                NabuParser.RULE_importDeclaration -> NabuImportDeclaration(node)
                NabuParser.RULE_singleTypeImportDeclaration -> NabuSingleTypeImportDeclaration(node)
                NabuParser.RULE_typeImportOnDemandDeclaration -> NabuTypeImportOnDemandDeclaration(node)
                NabuParser.RULE_singleStaticImportDeclaration -> NabuSingleStaticImportDeclaration(node)
                NabuParser.RULE_staticImportOnDemandDeclaration -> NabuStaticImportOnDemandDeclaration(node)
                NabuParser.RULE_topLevelClassOrInterfaceDeclaration -> NabuTopLevelClassOrInterfaceDeclaration(node)
                NabuParser.RULE_moduleDeclaration -> NabuModuleDeclaration(node)
                NabuParser.RULE_moduleDirective -> NabuModuleDirective(node)
                NabuParser.RULE_requiresModifier -> NabuRequiresModifier(node)
                NabuParser.RULE_classDeclaration -> NabuClassDeclarationWrapper(node)
                NabuParser.RULE_normalClassDeclaration -> NabuClassDeclaration(node, NabuElementKind.CLASS)
                NabuParser.RULE_classModifier -> NabuModifier(node)
                NabuParser.RULE_typeParameters -> NabuTypeParameters(node)
                NabuParser.RULE_typeParameterList -> NabuTypeParameterList(node)
                NabuParser.RULE_classExtends -> NabuClassExtends(node)
                NabuParser.RULE_classImplements -> NabuClassImplements(node)
                NabuParser.RULE_interfaceTypeList -> NabuInterfaceTypeList(node)
                NabuParser.RULE_classPermits -> NabuClassPermits(node)
                NabuParser.RULE_classBody -> NabuClassBody(node)
                NabuParser.RULE_classBodyDeclaration -> NabuClassBodyDeclaration(node)
                NabuParser.RULE_classMemberDeclaration -> NabuClassMemberDeclaration(node)
                NabuParser.RULE_fieldDeclaration -> NabuFieldDeclaration(node)
                NabuParser.RULE_fieldModifier -> NabuModifier(node)
                NabuParser.RULE_variableDeclaratorList -> NabuVariableDeclaratorList(node)
                NabuParser.RULE_variableDeclarator -> NabuVariableDeclarator(node)
                NabuParser.RULE_variableDeclaratorId -> NabuVariableDeclaratorId(node)
                NabuParser.RULE_variableInitializer -> NabuVariableInitializer(node)
                NabuParser.RULE_unannType -> NabuUnannType(node)
                NabuParser.RULE_unannPrimitiveType -> NabuUnannPrimitiveType(node)
                NabuParser.RULE_unannReferenceType -> NabuUnannReferenceType(node)
                NabuParser.RULE_unannClassOrInterfaceType -> NabuUnannClassOrInterfaceType(node)
                NabuParser.RULE_uCOIT -> NabuUCOIT(node)
                NabuParser.RULE_unannClassType -> NabuUnannClassType(node)
                NabuParser.RULE_unannInterfaceType -> NabuUnannInterfaceType(node)
                NabuParser.RULE_unannTypeVariable -> NabuUnannTypeVariable(node)
                NabuParser.RULE_unannArrayType -> NabuUnannArrayType(node)
                NabuParser.RULE_functionDeclaration -> NabuFunctionDeclaration(node)
                NabuParser.RULE_functionModifier -> NabuModifier(node)
                NabuParser.RULE_functionHeader -> NabuFunctionHeader(node)
                NabuParser.RULE_result -> NabuResult(node)
                NabuParser.RULE_functionDeclarator -> NabuFunctionDeclarator(node)
                NabuParser.RULE_receiverParameter -> NabuReceiverParameter(node)
                NabuParser.RULE_formalParameterList -> NabuFormalParameterList(node)
                NabuParser.RULE_formalParameter -> NabuFormalParameter(node)
                NabuParser.RULE_variableArityParameter -> NabuVariableArityParameter(node)
                NabuParser.RULE_variableModifier -> NabuModifier(node)
                NabuParser.RULE_throwsT -> NabuThrowsT(node)
                NabuParser.RULE_exceptionTypeList -> NabuExceptionTypeList(node)
                NabuParser.RULE_exceptionType -> NabuExceptionType(node)
                NabuParser.RULE_functionBody -> NabuFunctionBody(node)
                NabuParser.RULE_instanceInitializer -> NabuInstanceInitializer(node)
                NabuParser.RULE_staticInitializer -> NabuStaticInitializer(node)
                NabuParser.RULE_constructorDeclaration -> NabuConstructorDeclaration(node)
                NabuParser.RULE_constructorModifier -> NabuModifier(node)
                NabuParser.RULE_constructorDeclarator -> NabuConstructorDeclarator(node)
                NabuParser.RULE_simpleTypeName -> NabuSimpleTypeName(node)
                NabuParser.RULE_constructorBody -> NabuConstructorBody(node)
                NabuParser.RULE_explicitConstructorInvocation -> NabuExplicitConstructorInvocation(node)
                NabuParser.RULE_enumDeclaration -> NabuClassDeclaration(node, NabuElementKind.ENUM)
                NabuParser.RULE_enumBody -> NabuEnumBody(node)
                NabuParser.RULE_enumConstantList -> NabuEnumConstantList(node)
                NabuParser.RULE_enumConstant -> NabuEnumConstant(node)
                NabuParser.RULE_enumConstantModifier -> NabuModifier(node)
                NabuParser.RULE_enumBodyDeclarations -> NabuEnumBodyDeclarations(node)
                NabuParser.RULE_recordDeclaration -> NabuClassDeclaration(node, NabuElementKind.RECORD)
                NabuParser.RULE_recordHeader -> NabuRecordHeader(node)
                NabuParser.RULE_recordComponentList -> NabuRecordComponentList(node)
                NabuParser.RULE_recordComponent -> NabuRecordComponent(node)
                NabuParser.RULE_variableArityRecordComponent -> NabuVariableArityRecordComponent(node)
                NabuParser.RULE_recordComponentModifier -> NabuModifier(node)
                NabuParser.RULE_recordBody -> NabuRecordBody(node)
                NabuParser.RULE_recordBodyDeclaration -> NabuRecordBodyDeclaration(node)
                NabuParser.RULE_compactConstructorDeclaration -> NabuCompactConstructorDeclaration(node)
                NabuParser.RULE_interfaceDeclaration -> NabuInterfaceDeclaration(node)
                NabuParser.RULE_normalInterfaceDeclaration -> NabuNormalInterfaceDeclaration(node)
                NabuParser.RULE_interfaceModifier -> NabuModifier(node)
                NabuParser.RULE_interfaceExtends -> NabuInterfaceExtends(node)
                NabuParser.RULE_interfacePermits -> NabuInterfacePermits(node)
                NabuParser.RULE_interfaceBody -> NabuInterfaceBody(node)
                NabuParser.RULE_interfaceMemberDeclaration -> NabuInterfaceMemberDeclaration(node)
                NabuParser.RULE_constantDeclaration -> NabuConstantDeclaration(node)
                NabuParser.RULE_constantModifier -> NabuModifier(node)
                NabuParser.RULE_interfaceFunctionDeclaration -> NabuInterfaceFunctionDeclaration(node)
                NabuParser.RULE_interfaceFunctionModifier -> NabuModifier(node)
                NabuParser.RULE_annotationInterfaceDeclaration -> NabuAnnotationInterfaceDeclaration(node)
                NabuParser.RULE_annotationInterfaceBody -> NabuAnnotationInterfaceBody(node)
                NabuParser.RULE_annotationInterfaceMemberDeclaration -> NabuAnnotationInterfaceMemberDeclaration(node)
                NabuParser.RULE_annotationInterfaceElementDeclaration -> NabuAnnotationInterfaceElementDeclaration(node)
                NabuParser.RULE_annotationInterfaceElementModifier -> NabuModifier(node)
                NabuParser.RULE_defaultValue -> NabuDefaultValue(node)
                NabuParser.RULE_annotation -> NabuAnnotation(node)
                NabuParser.RULE_normalAnnotation -> NabuNormalAnnotation(node)
                NabuParser.RULE_elementValuePairList -> NabuStandardElement(node)
                NabuParser.RULE_elementValuePair -> NabuStandardElement(node)
                NabuParser.RULE_elementValue -> NabuStandardElement(node)
                NabuParser.RULE_elementValueArrayInitializer -> NabuStandardElement(node)
                NabuParser.RULE_elementValueList -> NabuStandardElement(node)
                NabuParser.RULE_markerAnnotation -> NabuStandardElement(node)
                NabuParser.RULE_singleElementAnnotation -> NabuStandardElement(node)
                NabuParser.RULE_arrayInitializer -> NabuStandardElement(node)
                NabuParser.RULE_variableInitializerList -> NabuStandardElement(node)
                NabuParser.RULE_block -> NabuStandardElement(node)
                NabuParser.RULE_blockStatements -> NabuStandardElement(node)
                NabuParser.RULE_blockStatement -> NabuStandardElement(node)
                NabuParser.RULE_localClassOrInterfaceDeclaration -> NabuStandardElement(node)
                NabuParser.RULE_localVariableDeclaration -> NabuStandardElement(node)
                NabuParser.RULE_localVariableType -> NabuStandardElement(node)
                NabuParser.RULE_localVariableDeclarationStatement -> NabuStandardElement(node)
                NabuParser.RULE_statement -> NabuStandardElement(node)
                NabuParser.RULE_statementNoShortIf -> NabuStandardElement(node)
                NabuParser.RULE_statementWithoutTrailingSubstatement -> NabuStandardElement(node)
                NabuParser.RULE_emptyStatement_ -> NabuStandardElement(node)
                NabuParser.RULE_labeledStatement -> NabuStandardElement(node)
                NabuParser.RULE_labeledStatementNoShortIf -> NabuStandardElement(node)
                NabuParser.RULE_expressionStatement -> NabuStandardElement(node)
                NabuParser.RULE_statementExpression -> NabuStandardElement(node)
                NabuParser.RULE_ifThenStatement -> NabuStandardElement(node)
                NabuParser.RULE_ifThenElseStatement -> NabuStandardElement(node)
                NabuParser.RULE_ifThenElseStatementNoShortIf -> NabuStandardElement(node)
                NabuParser.RULE_assertStatement -> NabuStandardElement(node)
                NabuParser.RULE_switchStatement -> NabuStandardElement(node)
                NabuParser.RULE_switchBlock -> NabuStandardElement(node)
                NabuParser.RULE_switchRule -> NabuStandardElement(node)
                NabuParser.RULE_switchBlockStatementGroup -> NabuStandardElement(node)
                NabuParser.RULE_switchLabel -> NabuStandardElement(node)
                NabuParser.RULE_caseConstant -> NabuStandardElement(node)
                NabuParser.RULE_whileStatement -> NabuStandardElement(node)
                NabuParser.RULE_whileStatementNoShortIf -> NabuStandardElement(node)
                NabuParser.RULE_doStatement -> NabuStandardElement(node)
                NabuParser.RULE_forStatement -> NabuStandardElement(node)
                NabuParser.RULE_forStatementNoShortIf -> NabuStandardElement(node)
                NabuParser.RULE_basicForStatement -> NabuStandardElement(node)
                NabuParser.RULE_basicForStatementNoShortIf -> NabuStandardElement(node)
                NabuParser.RULE_forInit -> NabuStandardElement(node)
                NabuParser.RULE_forUpdate -> NabuStandardElement(node)
                NabuParser.RULE_statementExpressionList -> NabuStandardElement(node)
                NabuParser.RULE_enhancedForStatement -> NabuStandardElement(node)
                NabuParser.RULE_enhancedForStatementNoShortIf -> NabuStandardElement(node)
                NabuParser.RULE_breakStatement -> NabuStandardElement(node)
                NabuParser.RULE_continueStatement -> NabuStandardElement(node)
                NabuParser.RULE_returnStatement -> NabuStandardElement(node)
                NabuParser.RULE_throwStatement -> NabuStandardElement(node)
                NabuParser.RULE_synchronizedStatement -> NabuStandardElement(node)
                NabuParser.RULE_tryStatement -> NabuStandardElement(node)
                NabuParser.RULE_catches -> NabuStandardElement(node)
                NabuParser.RULE_catchClause -> NabuStandardElement(node)
                NabuParser.RULE_catchFormalParameter -> NabuStandardElement(node)
                NabuParser.RULE_catchType -> NabuStandardElement(node)
                NabuParser.RULE_finallyBlock -> NabuStandardElement(node)
                NabuParser.RULE_tryWithResourcesStatement -> NabuStandardElement(node)
                NabuParser.RULE_resourceSpecification -> NabuStandardElement(node)
                NabuParser.RULE_resourceList -> NabuStandardElement(node)
                NabuParser.RULE_resource -> NabuStandardElement(node)
                NabuParser.RULE_variableAccess -> NabuStandardElement(node)
                NabuParser.RULE_yieldStatement -> NabuStandardElement(node)
                NabuParser.RULE_pattern -> NabuStandardElement(node)
                NabuParser.RULE_typePattern -> NabuStandardElement(node)
                NabuParser.RULE_expression -> NabuExpression(node)
                NabuParser.RULE_primary -> NabuPrimary(node)
                NabuParser.RULE_primaryNoNewArray -> NabuPrimaryNoNewArray(node)
                NabuParser.RULE_pNNA -> NabuStandardElement(node)
                NabuParser.RULE_classLiteral -> NabuClassLiteral(node)
                NabuParser.RULE_classInstanceCreationExpression -> NabuStandardElement(node)
                NabuParser.RULE_unqualifiedClassInstanceCreationExpression -> NabuStandardElement(node)
                NabuParser.RULE_classOrInterfaceTypeToInstantiate -> NabuStandardElement(node)
                NabuParser.RULE_typeArgumentsOrDiamond -> NabuStandardElement(node)
                NabuParser.RULE_arrayCreationExpression -> NabuStandardElement(node)
                NabuParser.RULE_arrayCreationExpressionWithoutInitializer -> NabuStandardElement(node)
                NabuParser.RULE_arrayCreationExpressionWithInitializer -> NabuStandardElement(node)
                NabuParser.RULE_dimExprs -> NabuStandardElement(node)
                NabuParser.RULE_dimExpr -> NabuStandardElement(node)
                NabuParser.RULE_arrayAccess -> NabuStandardElement(node)
                NabuParser.RULE_fieldAccess -> NabuStandardElement(node)
                NabuParser.RULE_functionInvocation -> NabuStandardElement(node)
                NabuParser.RULE_argumentList -> NabuStandardElement(node)
                NabuParser.RULE_functionReference -> NabuStandardElement(node)
                NabuParser.RULE_postfixExpression -> NabuPostfixExpression(node)
                NabuParser.RULE_pfE -> NabuStandardElement(node)
                NabuParser.RULE_postIncrementExpression -> NabuStandardElement(node)
                NabuParser.RULE_postDecrementExpression -> NabuStandardElement(node)
                NabuParser.RULE_unaryExpression -> NabuUnaryExpression(node)
                NabuParser.RULE_preIncrementExpression -> NabuStandardElement(node)
                NabuParser.RULE_preDecrementExpression -> NabuStandardElement(node)
                NabuParser.RULE_unaryExpressionNotPlusMinus -> NabuUnaryExpressionNotPlusMinus(node)
                NabuParser.RULE_castExpression -> NabuStandardElement(node)
                NabuParser.RULE_multiplicativeExpression -> NabuMultiplicativeExpression(node)
                NabuParser.RULE_additiveExpression -> NabuAdditiveExpression(node)
                NabuParser.RULE_shiftExpression -> NabuShiftExpression(node)
                NabuParser.RULE_relationalExpression -> NabuRelationalExpression(node)
                NabuParser.RULE_equalityExpression -> NabuEqualityExpression(node)
                NabuParser.RULE_andExpression -> NabuAndExpression(node)
                NabuParser.RULE_exclusiveOrExpression -> NabuExclusiveOrExpression(node)
                NabuParser.RULE_inclusiveOrExpression -> NabuInclusiveOrExpression(node)
                NabuParser.RULE_conditionalAndExpression -> NabuConditionalAndExpression(node)
                NabuParser.RULE_conditionalOrExpression -> NabuConditionalOrExpression(node)
                NabuParser.RULE_conditionalExpression -> NabuConditionalExpression(node)
                NabuParser.RULE_assignmentExpression -> NabuAssignmentExpression(node)
                NabuParser.RULE_assignment -> NabuStandardElement(node)
                NabuParser.RULE_leftHandSide -> NabuStandardElement(node)
                NabuParser.RULE_assignmentOperator -> NabuStandardElement(node)
                NabuParser.RULE_lambdaExpression -> NabuStandardElement(node)
                NabuParser.RULE_lambdaParameters -> NabuStandardElement(node)
                NabuParser.RULE_lambdaParameterList -> NabuStandardElement(node)
                NabuParser.RULE_lambdaParameter -> NabuStandardElement(node)
                NabuParser.RULE_lambdaParameterType -> NabuStandardElement(node)
                NabuParser.RULE_lambdaBody -> NabuStandardElement(node)
                NabuParser.RULE_switchExpression -> NabuStandardElement(node)
                NabuParser.RULE_constantExpression -> NabuStandardElement(node)
                else -> NabuStandardElement(node)
            }
        }

        return NabuStandardElement(node)
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return NabuFile(viewProvider)
    }
}