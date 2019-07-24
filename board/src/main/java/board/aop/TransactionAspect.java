package board.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionAspect {
	
	// 트랜잭션 설정할 때 사용되는 설정값을 상수로 지정 
	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	// 비즈니스 로직이 수행되는 모든 ServiceImpl 클래스의 모든 메서드를 지정
	private static final String AOP_TRANSACTION_EXPRESSION = "execution(* board..service.*Impl.*(..))";
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public TransactionInterceptor transactionAdvice() {
		MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		
		// 트랜잭션의 이름설정 
		transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
		// 트랜잭션에서 롤백하는 rule 설정 -> 여기서는 예외 발생시 롤백하도록 지정함. (Exception.class)
		transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		
		source.setTransactionAttribute(transactionAttribute);
		
		return new TransactionInterceptor(transactionManager, source);
	}
	
	@Bean
	public Advisor transactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		
		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
		
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}
}
