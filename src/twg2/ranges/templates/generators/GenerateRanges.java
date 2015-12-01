package twg2.ranges.templates.generators;

import java.io.IOException;
import java.util.Arrays;

import org.stringtemplate.v4.ST;

import twg2.ranges.templates.RangeInfo;
import twg2.ranges.templates.SearchSetInfo;
import twg2.template.codeTemplate.primitiveTemplate.PrimitiveTemplates;
import twg2.template.codeTemplate.render.StringTemplatesUtil;
import twg2.template.codeTemplate.render.TemplateImports;

/**
 * @author TeamworkGuy2
 * @since 2014-12-24
 */
public class GenerateRanges {
	private static String templateDir = "src/twg2/ranges/templates/";
	private static TemplateImports importsMapper = TemplateImports.emptyInst();


	public static final void generateRangeClasses() throws IOException {
		SearchSetInfo charRange = PrimitiveTemplates.newCharTemplate(new SearchSetInfo(), "", "twg2.ranges");
		SearchSetInfo intRange = PrimitiveTemplates.newIntTemplate(new SearchSetInfo(), "", "twg2.ranges");
		SearchSetInfo floatRange = PrimitiveTemplates.newFloatTemplate(new SearchSetInfo(), "", "twg2.ranges");
		SearchSetInfo[] infos = new SearchSetInfo[] { charRange, intRange, floatRange };

		// generates XyzRange interfaces
		charRange.className = "CharRange";
		intRange.className = "IntRange";
		floatRange.className = "FloatRange";

		generateRanges(infos);

		// generates XyzSearcher interfaces
		charRange.className = "CharSearcher";
		intRange.className = "IntSearcher";
		floatRange.className = "FloatSearcher";

		generateSearchers(infos);

		// generates XyzRangeSearcher interfaces
		charRange.className = "CharRangeSearcher";
		charRange.implementClassNames = Arrays.asList("CharRange", "CharSearcher");

		intRange.className = "IntRangeSearcher";
		intRange.implementClassNames = Arrays.asList("IntRange", "IntSearcher");

		floatRange.className = "FloatRangeSearcher";
		floatRange.implementClassNames = Arrays.asList("FloatRange", "FloatSearcher");

		generateRangeSearchers(infos);

		// generates XyzRangeSearcherMutable interfaces
		charRange.className = "CharRangeSearcherMutable";
		charRange.implementClassNames = Arrays.asList("CharRangeSearcher");
		charRange.immutableClassName = "CharRangeSearcher";

		intRange.className = "IntRangeSearcherMutable";
		intRange.implementClassNames = Arrays.asList("IntRangeSearcher");
		intRange.immutableClassName = "IntRangeSearcher";

		floatRange.className = "FloatRangeSearcherMutable";
		floatRange.implementClassNames = Arrays.asList("FloatRangeSearcher");
		floatRange.immutableClassName = "FloatRangeSearcher";

		generateRangeSearcherMutables(infos);

		// generates XyzRangeSearcherMutableImpl classes
		charRange.className = "CharRangeSearcherMutableImpl";
		charRange.primitiveListImpl = "twg2.collections.primitiveCollections.CharArrayList";
		charRange.primitiveListInterface = "twg2.collections.primitiveCollections.CharList";
		charRange.implementClassNames = Arrays.asList("CharRangeSearcherMutable");
		charRange.immutableClassName = "CharRangeSearcher";

		intRange.className = "IntRangeSearcherMutableImpl";
		intRange.primitiveListImpl = "twg2.collections.primitiveCollections.IntArrayList";
		intRange.primitiveListInterface = "twg2.collections.primitiveCollections.IntList";
		intRange.implementClassNames = Arrays.asList("IntRangeSearcherMutable");
		intRange.immutableClassName = "IntRangeSearcher";

		floatRange.className = "FloatRangeSearcherMutableImpl";
		floatRange.primitiveListImpl = "twg2.collections.primitiveCollections.FloatArrayList";
		floatRange.primitiveListInterface = "twg2.collections.primitiveCollections.FloatList";
		floatRange.implementClassNames = Arrays.asList("FloatRangeSearcherMutable");
		floatRange.immutableClassName = "FloatRangeSearcher";

		generateRangeSearcherMutableImpls(infos);

		// generates XyzSearchSet classes
		charRange.className = "CharSearchSet";
		charRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.CharListSorted");
		charRange.implementClassNames = Arrays.asList("CharSearcher");
		charRange.valuesCollectionClassName = "CharListSorted";
		charRange.rangesCollectionClassName = "CharRangeSearcherMutableImpl";

		intRange.className = "IntSearchSet";
		intRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.IntListSorted");
		intRange.implementClassNames = Arrays.asList("IntSearcher");
		intRange.valuesCollectionClassName = "IntListSorted";
		intRange.rangesCollectionClassName = "IntRangeSearcherMutableImpl";

		floatRange.className = "FloatSearchSet";
		floatRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.FloatListSorted");
		floatRange.implementClassNames = Arrays.asList("FloatSearcher");
		floatRange.valuesCollectionClassName = "FloatListSorted";
		floatRange.rangesCollectionClassName = "FloatRangeSearcherMutableImpl";

		generateRangeSearcherSet(infos);

		charRange.className = "CharSearcherMutable";
		charRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.CharListSorted");
		charRange.implementClassNames = Arrays.asList("CharSearcher");

		intRange.className = "IntSearcherMutable";
		intRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.IntListSorted");
		intRange.implementClassNames = Arrays.asList("IntSearcher");

		floatRange.className = "FloatSearcherMutable";
		floatRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.FloatListSorted");
		floatRange.implementClassNames = Arrays.asList("FloatSearcher");

		generateSearcherMutable(infos);
	}


	public static final void generateRanges(RangeInfo... infos) throws IOException {
		ST tmpl = StringTemplatesUtil.fileTemplate(templateDir + "TRange.stg", "TRange", importsMapper);
		for(RangeInfo info : infos) {
			StringTemplatesUtil.renderClass(tmpl, "var", info);
		}
	}


	public static final void generateSearchers(RangeInfo... infos) throws IOException {
		ST tmpl = StringTemplatesUtil.fileTemplate(templateDir + "TSearcher.stg", "TSearcher", importsMapper);
		for(RangeInfo info : infos) {
			StringTemplatesUtil.renderClass(tmpl, "var", info);
		}
	}


	public static final void generateRangeSearchers(RangeInfo... infos) throws IOException {
		ST tmpl = StringTemplatesUtil.fileTemplate(templateDir + "TRangeSearcher.stg", "TRangeSearcher", importsMapper);
		for(RangeInfo info : infos) {
			StringTemplatesUtil.renderClass(tmpl, "var", info);
		}
	}


	public static final void generateRangeSearcherMutables(RangeInfo... infos) throws IOException {
		ST tmpl = StringTemplatesUtil.fileTemplate(templateDir + "TRangeSearcherMutable.stg", "TRangeSearcherMutable", importsMapper);
		for(RangeInfo info : infos) {
			StringTemplatesUtil.renderClass(tmpl, "var", info);
		}
	}


	public static final void generateRangeSearcherMutableImpls(RangeInfo... infos) throws IOException {
		ST tmpl = StringTemplatesUtil.fileTemplate(templateDir + "TRangeSearcherMutableImpl.stg", "TRangeSearcherMutableImpl", importsMapper);
		for(RangeInfo info : infos) {
			StringTemplatesUtil.renderClass(tmpl, "var", info);
		}
	}


	public static final void generateRangeSearcherSet(SearchSetInfo... infos) throws IOException {
		ST tmpl = StringTemplatesUtil.fileTemplate(templateDir + "TSearchSet.stg", "TSearchSet", importsMapper);
		for(RangeInfo info : infos) {
			StringTemplatesUtil.renderClass(tmpl, "var", info);
		}
	}


	public static final void generateSearcherMutable(SearchSetInfo... infos) throws IOException {
		ST tmpl = StringTemplatesUtil.fileTemplate(templateDir + "TSearcherMutable.stg", "TSearcherMutable", importsMapper);
		for(RangeInfo info : infos) {
			StringTemplatesUtil.renderClass(tmpl, "var", info);
		}
	}


	public static void main(String[] args) throws IOException {
		generateRangeClasses();
	}

}
