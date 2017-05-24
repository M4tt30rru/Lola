package il.ac.technion.cs.ssdl.lola.utils;
import java.util.Map;

import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.formatter.*;
import org.eclipse.jface.text.*;
import org.eclipse.text.edits.*;
/**
 * @author Ori Marcovitch
 * @since Nov 13, 2016
 */
public class format {
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static String code(final String code) {
		final Map options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_7);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_7);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
		options.put(DefaultCodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ENUM_CONSTANTS,
				DefaultCodeFormatterConstants.createAlignmentValue(true, DefaultCodeFormatterConstants.WRAP_ONE_PER_LINE,
						DefaultCodeFormatterConstants.INDENT_ON_COLUMN));
		final CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(options);
		final TextEdit textEdit = codeFormatter.format(CodeFormatter.K_UNKNOWN, code, 0, code.length(), 0, System.getProperty("line.separator"));
		final IDocument doc = new Document(code);
		try {
			if (textEdit != null)
				textEdit.apply(doc);
		} catch (final MalformedTreeException e) {
			e.printStackTrace();
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
		return doc.get();
	}
}
