package test;

import beans.LookupElem;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import inter.CompletionContributorParameter;
import inter.GotoCompletionContributor;
import inter.GotoCompletionProviderInterface;
import util.GotoCompletionUtil;
import util.MethodMatcher;
import util.PsiElementUtil;

import java.util.ArrayList;

public class UnitTest {
    //    检查方法名(帮助方法)
    public void testCheckFunction(CompletionParameters completionParameters) {
        PsiElement originalPosition = completionParameters.getOriginalPosition();
        PsiElement parent = originalPosition.getParent();
        boolean config1 = PsiElementUtil.isFunctionReference(parent, "config", 0);
        System.out.println("psi2 is config" + config1);
    }

    //检查方法名(类方法)
    public MethodMatcher.MethodMatchParameter testCheckMethod(CompletionParameters completionParameters) {
        MethodMatcher.CallToSignature[] CONFIG = new MethodMatcher.CallToSignature[]{
                new MethodMatcher.CallToSignature("\\think\\Config", "get"),
                new MethodMatcher.CallToSignature("\\think\\Config", "has"),
                new MethodMatcher.CallToSignature("\\think\\Config", "set"),
//                new MethodMatcher.CallToSignature("\\Illuminate\\Config\\Repository", "setParsedKey"),
        };
        PsiElement originalPosition = completionParameters.getOriginalPosition();
        PsiElement parent = originalPosition.getParent();
        MethodMatcher.MethodMatchParameter matchedSignatureWithDepth = MethodMatcher.getMatchedSignatureWithDepth(parent, CONFIG);
        System.out.println(matchedSignatureWithDepth);
        return matchedSignatureWithDepth;
    }

    //    提供提示
    public void testProvide(CompletionResultSet completionResultSet) {
        ArrayList<LookupElem> elems = new ArrayList<>();
        elems.add(new LookupElem("aaaaa"));
        elems.add(new LookupElem("bbbbb"));
        elems.add(new LookupElem("ccccc"));
        completionResultSet.addAllElements(elems);
//        System.out.println(elems);
    }

    //提供提示数据
    public void testGetProvideData(CompletionParameters completionParameters, CompletionResultSet completionResultSet) {
        PsiElement psiElement = completionParameters.getOriginalPosition();
        CompletionContributorParameter parameter = null;
        for (GotoCompletionContributor contributor : GotoCompletionUtil.getContributors(psiElement)) {
            GotoCompletionProviderInterface formReferenceCompletionContributor = contributor.getProvider(psiElement);
            if (formReferenceCompletionContributor != null) {
                completionResultSet.addAllElements(
                        formReferenceCompletionContributor.getLookupElements()
                );

                if (parameter == null) {
//                    parameter = new CompletionContributorParameter(completionParameters, processingContext, completionResultSet);
                }

                formReferenceCompletionContributor.getLookupElements(parameter);
            }
        }
    }
}
