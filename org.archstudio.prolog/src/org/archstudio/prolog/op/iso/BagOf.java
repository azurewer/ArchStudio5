package org.archstudio.prolog.op.iso;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.archstudio.prolog.engine.PrologUtils;
import org.archstudio.prolog.engine.ProofContext;
import org.archstudio.prolog.engine.UnificationContext;
import org.archstudio.prolog.engine.UnificationEngine;
import org.archstudio.prolog.op.Executable;
import org.archstudio.prolog.term.ComplexTerm;
import org.archstudio.prolog.term.ListTerm;
import org.archstudio.prolog.term.Term;
import org.archstudio.prolog.term.VariableTerm;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class BagOf extends ComplexTerm implements Executable {

	public BagOf(String name, List<? extends Term> terms) {
		super(name, 3, terms);
	}

	@Override
	public Iterable<Map<VariableTerm, Term>> execute(final ProofContext proofContext,
			final UnificationEngine unificationEngine, Term source, final Map<VariableTerm, Term> variables) {

		Term template = getTerm(0);
		Term goal = getTerm(1);
		Term bag = getTerm(2);

		Set<VariableTerm> nonbinningVariables = PrologUtils
				.extractVariables(Sets.<VariableTerm> newHashSet(), template);
		while (goal instanceof ComplexTerm && ((ComplexTerm) goal).getFunctor().equals("^")) {
			ComplexTerm complexGoal = (ComplexTerm) goal;
			nonbinningVariables.add((VariableTerm) complexGoal.getTerm(0));
			goal = complexGoal.getTerm(1);
		}

		// Strangely, while not necessary, the generic type of ArrayListMultimap.<> must be declared explicitly or the eclipse compiler crashes
		Multimap<Map<VariableTerm, Term>, Term> results = ArrayListMultimap.<Map<VariableTerm, Term>, Term> create();
		for (Map<VariableTerm, Term> result : PrologUtils.resolveExecutable(proofContext, goal, variables).execute(
				proofContext, unificationEngine, goal, variables)) {
			Map<VariableTerm, Term> binningVariables = Maps.newHashMap(result);
			binningVariables.keySet().removeAll(nonbinningVariables);
			results.get(binningVariables).add(template.resolve(proofContext, result));
		}

		Collection<Map<VariableTerm, Term>> finalResults = Lists.newArrayList();
		for (Entry<Map<VariableTerm, Term>, Collection<Term>> entry : results.asMap().entrySet()) {
			UnificationContext context = new UnificationContext(proofContext, bag,
					ListTerm.asListTerm(entry.getValue()), entry.getKey());
			if (unificationEngine.unifies(proofContext, context)) {
				finalResults.add(context.variables);
			}
		}

		return finalResults;
	}
}
