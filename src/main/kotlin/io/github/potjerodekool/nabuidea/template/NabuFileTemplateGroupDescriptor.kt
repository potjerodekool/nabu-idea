package io.github.potjerodekool.nabuidea.template

import com.intellij.ide.fileTemplates.FileTemplateDescriptor
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory


class NabuFileTemplateGroupDescriptor : FileTemplateGroupDescriptorFactory {

    override fun getFileTemplatesDescriptor(): FileTemplateGroupDescriptor {

        val group = FileTemplateGroupDescriptor("Maven", null) //NON-NLS
        group.addTemplate(FileTemplateDescriptor("Nabu class.nabu", null))

        return group
    }

}
