package templates.generators;

import java.io.IOException;
import java.util.Arrays;

import org.stringtemplate.v4.ST;

import templates.RangeInfo;
import templates.SearchSetInfo;
import codeTemplate.PrimitiveTemplates;
import codeTemplate.TemplateRender;

/**
 * @author TeamworkGuy2
 * @since 2014-12-24
 */
public class GenerateRanges {
	private static String templateDir = "src/templates/";


	public static final void generateRangeClasses() throws IOException {
		SearchSetInfo charRange = PrimitiveTemplates.newCharTemplate(new SearchSetInfo(), "", "ranges");
		SearchSetInfo intRange = PrimitiveTemplates.newIntTemplate(new SearchSetInfo(), "", "ranges");
		SearchSetInfo floatRange = PrimitiveTemplates.newFloatTemplate(new SearchSetInfo(), "", "ranges");
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
		charRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.CharListSorted;");
		charRange.implementClassNames = Arrays.asList("CharSearcher");
		charRange.valuesCollectionClassName = "CharListSorted";
		charRange.rangesCollectionClassName = "CharRangeSearcherMutableImpl";

		intRange.className = "IntSearchSet";
		intRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.IntListSorted;");
		intRange.implementClassNames = Arrays.asList("IntSearcher");
		intRange.valuesCollectionClassName = "IntListSorted";
		intRange.rangesCollectionClassName = "IntRangeSearcherMutableImpl";

		floatRange.className = "FloatSearchSet";
		floatRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.FloatListSorted;");
		floatRange.implementClassNames = Arrays.asList("FloatSearcher");
		floatRange.valuesCollectionClassName = "FloatListSorted";
		floatRange.rangesCollectionClassName = "FloatRangeSearcherMutableImpl";

		generateRangeSearcherSet(infos);

		charRange.className = "CharSearcherMutable";
		charRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.CharListSorted;");
		charRange.implementClassNames = Arrays.asList("CharSearcher");

		intRange.className = "IntSearcherMutable";
		intRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.IntListSorted;");
		intRange.implementClassNames = Arrays.asList("IntSearcher");

		floatRange.className = "FloatSearcherMutable";
		floatRange.importStatements = Arrays.asList("import twg2.collections.primitiveCollections.FloatListSorted;");
		floatRange.implementClassNames = Arrays.asList("FloatSearcher");

		generateSearcherMutable(infos);
	}


	public static final void generateRanges(RangeInfo... infos) throws IOException {
		ST tmpl = TemplateRender.createTemplate(templateDir + "TRange.stg", "TRange");
		for(RangeInfo info : infos) {
			TemplateRender.renderClassTemplate(tmpl, info);
		}
	}


	public static final void generateSearchers(RangeInfo... infos) throws IOException {
		ST tmpl = TemplateRender.createTemplate(templateDir + "TSearcher.stg", "TSearcher");
		for(RangeInfo info : infos) {
			TemplateRender.renderClassTemplate(tmpl, info);
		}
	}


	public static final void generateRangeSearchers(RangeInfo... infos) throws IOException {
		ST tmpl = TemplateRender.createTemplate(templateDir + "TRangeSearcher.stg", "TRangeSearcher");
		for(RangeInfo info : infos) {
			TemplateRender.renderClassTemplate(tmpl, info);
		}
	}


	public static final void generateRangeSearcherMutables(RangeInfo... infos) throws IOException {
		ST tmpl = TemplateRender.createTemplate(templateDir + "TRangeSearcherMutable.stg", "TRangeSearcherMutable");
		for(RangeInfo info : infos) {
			TemplateRender.renderClassTemplate(tmpl, info);
		}
	}


	public static final void generateRangeSearcherMutableImpls(RangeInfo... infos) throws IOException {
		ST tmpl = TemplateRender.createTemplate(templateDir + "TRangeSearcherMutableImpl.stg", "TRangeSearcherMutableImpl");
		for(RangeInfo info : infos) {
			TemplateRender.renderClassTemplate(tmpl, info);
		}
	}


	public static final void generateRangeSearcherSet(SearchSetInfo... infos) throws IOException {
		ST tmpl = TemplateRender.createTemplate(templateDir + "TSearchSet.stg", "TSearchSet");
		for(RangeInfo info : infos) {
			TemplateRender.renderClassTemplate(tmpl, info);
		}
	}


	public static final void generateSearcherMutable(SearchSetInfo... infos) throws IOException {
		ST tmpl = TemplateRender.createTemplate(templateDir + "TSearcherMutable.stg", "TSearcherMutable");
		for(RangeInfo info : infos) {
			TemplateRender.renderClassTemplate(tmpl, info);
		}
	}


	public static void main(String[] args) throws IOException {
		generateRangeClasses();
	}

}
