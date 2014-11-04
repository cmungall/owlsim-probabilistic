package org.monarchinitiative.owlsim.compute.matcher;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.monarchinitiative.owlsim.compute.matcher.impl.GMProfileMatcher;
import org.monarchinitiative.owlsim.compute.matcher.impl.GMProfileMatcher.GMProfileMatcherConfig;
import org.monarchinitiative.owlsim.eval.TestQuery;
import org.monarchinitiative.owlsim.kb.NonUniqueLabelException;
import org.monarchinitiative.owlsim.kb.filter.UnknownFilterException;
import org.monarchinitiative.owlsim.model.match.ProfileQuery;
import org.monarchinitiative.owlsim.model.match.impl.ProfileQueryImpl;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * Tests a ProfileMatcher using ultra-simple ontoligy
 * 
 *     Thing
 *     /    \
 *    a      b
 *    
 * 
 * With 3 'genes', with annotations {a}, {b}, and {a,b}
 * 
 *
 */
public class AbstractGMProfileMatcherTest extends AbstractProfileMatcherTest {

	private Logger LOG = Logger.getLogger(AbstractGMProfileMatcherTest.class);

	protected void search(String expectedId, int maxRank,
			double resampleProbability,
			String... labels) throws NonUniqueLabelException, OWLOntologyCreationException, FileNotFoundException, UnknownFilterException {
		GMProfileMatcherConfig config = new GMProfileMatcherConfig();
		config.q = resampleProbability;
		GMProfileMatcher profileMatcher = (GMProfileMatcher) GMProfileMatcher.create(kb, config);
		Set<String> qids = new HashSet<String>();
		for (String label: labels) {
			qids.add(getId(label));
		}
		LOG.info("QIDS="+qids);
		LOG.info("Resample proabability q="+profileMatcher.getResampleProbability());
		ProfileQuery q = ProfileQueryImpl.create(qids);
		TestQuery tq = new TestQuery(q, getId(expectedId), maxRank);
		evaluateTestQuery(profileMatcher, tq);
	}	







}
