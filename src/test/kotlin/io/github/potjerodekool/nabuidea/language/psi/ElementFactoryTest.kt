package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.lang.LanguageExtension
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiBuilderFactory
import com.intellij.lang.impl.PsiBuilderFactoryImpl
import com.intellij.mock.*
import com.intellij.openapi.Disposable
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.impl.FileDocumentManagerBase
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.impl.ProgressManagerImpl
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.impl.PsiFileFactoryImpl
import io.github.potjerodekool.nabuidea.NabuLanguage
import io.github.potjerodekool.nabuidea.language.NabuParserDefinition
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementFactory
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertNotNull


class ElementFactoryTest {

    @Test
    fun createTypeIdentifier() {
        val disposable = Mockito.mock<Disposable>()
        val app = MockApplication.setUp(disposable)
        val appContainer = app.picoContainer

        val project = MockProjectEx(disposable)
        val picoContainer = project.picoContainer

        val myPsiManager = MockPsiManager(project)
        val myFileFactory = PsiFileFactoryImpl(myPsiManager)
        picoContainer.registerComponentInstance(
            PsiFileFactory::class.java.name,
            myFileFactory
        )

        val editorFactory = MockEditorFactory()
        picoContainer.registerComponentInstance(EditorFactory::class.java, editorFactory)
        app.registerService(FileDocumentManager::class.java,
            MockFileDocumentManagerImpl(FileDocumentManagerBase.HARD_REF_TO_DOCUMENT_KEY, editorFactory::createDocument));

        app.registerService(PsiBuilderFactory::class.java, PsiBuilderFactoryImpl())

        val progressManagerComponent = appContainer.getComponentAdapter(ProgressManager::class.java.getName())
        if (progressManagerComponent == null) {
          appContainer.registerComponentInstance(ProgressManager::class.java.getName(), ProgressManagerImpl())
        }

        val cacheKeyField = LanguageExtension::class.java.getDeclaredField("cacheKey")
        cacheKeyField.trySetAccessible()
        val cacheKey = cacheKeyField.get(LanguageParserDefinitions.INSTANCE) as Key<ParserDefinition>

        NabuLanguage.putUserData(cacheKey, NabuParserDefinition())

        val typeIdentifier = NabuElementFactory.createTypeIdentifier(project, "Myclass")
        assertNotNull(typeIdentifier)
    }

}