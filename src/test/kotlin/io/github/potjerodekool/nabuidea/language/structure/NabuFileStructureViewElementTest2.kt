package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.lang.*
import com.intellij.lang.impl.PsiBuilderFactoryImpl
import com.intellij.mock.*
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.impl.FileDocumentManagerBase
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.impl.ProgressManagerImpl
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.PsiFileFactoryImpl
import com.intellij.testFramework.LightVirtualFile
import io.github.potjerodekool.nabuidea.NabuLanguage
import io.github.potjerodekool.nabuidea.language.NabuParserDefinition
import io.github.potjerodekool.nabuidea.language.highlight.NabuSyntaxHighlighter
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFileImpl
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import org.junit.Before
import org.junit.Test
import java.nio.charset.StandardCharsets
import kotlin.test.Ignore
import kotlin.test.assertFalse

class NabuFileStructureViewElementTest2 {

    private lateinit var project: MockProjectEx
    private lateinit var myPsiManager: MockPsiManager
    private lateinit var myFileFactory: PsiFileFactoryImpl

    @Before
    fun setup() {
        val disposable = TestDisposable()
        project = MockProjectEx(disposable)
        myPsiManager = MockPsiManager(project)
        myFileFactory = PsiFileFactoryImpl(myPsiManager)

        val app = MockApplication.setUp(disposable)
        ApplicationManager.setApplication(app, disposable)
        val editorFactory = MockEditorFactory()

        app.registerService(
            FileDocumentManager::class.java,
            MockFileDocumentManagerImpl(
                FileDocumentManagerBase.HARD_REF_TO_DOCUMENT_KEY,
                editorFactory::createDocument
            )
        )

        app.registerService(PsiBuilderFactory::class.java, PsiBuilderFactoryImpl())
        app.registerService(ProgressManager::class.java, ProgressManagerImpl())


        val keyField = LanguageExtension::class.java.getDeclaredField("cacheKey")
        keyField.trySetAccessible()
        val parserDefinitionKey = keyField.get(LanguageParserDefinitions.INSTANCE) as Key<ParserDefinition>

        NabuLanguage.putUserData(
            parserDefinitionKey,
            NabuParserDefinition()
        )
    }

    @Ignore
    @Test
    fun test() {
        val file = createFile(
            """
           public class MyClass {
            fun myFunction() {
            }
           }
        """
        ) as NabuFileImpl

        val fileElement = NabuFileStructureViewElement(file)

        val cu = SimplePsiTreeUtils.getCompilationUnit(file)

        if (cu != null) {
            val topLevels = SimplePsiTreeUtils.getTopLevelDeclarations(cu)
            val normalClassDeclaration = topLevels[0] as NabuClassDeclaration
            val typeIdentifier = normalClassDeclaration.typeIdentifier()
            println(typeIdentifier)


            IconUtils.getModifierIcon(
                normalClassDeclaration.classModifiers(),
                ElementType.CLASS
            )
        }

        val children = fileElement.children
        assertFalse(children.isEmpty())

        NabuSyntaxHighlighter()
    }


    fun createFile(virtualFile: LightVirtualFile): PsiFile? {
        return myFileFactory.trySetupPsiForFile(virtualFile, NabuLanguage, true, false)
    }

    fun createFile(code: String): PsiFile? {
        val virtualFile = LightVirtualFile("MyFile", NabuLanguage, code)
        virtualFile.setCharset(StandardCharsets.UTF_8)
        return createFile(virtualFile)
    }
}

class TestDisposable : Disposable {

    private var disposed = false

    override fun dispose() {
        disposed = true
    }

}