**좋은 객체 지향 설계의 5가지 원칙**

이 중 3가지 SRP, DIP, OCP를 적용시켜보자.

1. SRP (단일 책임 원칙) : 한 클래스는 하나의 책임만 가져야 한다.
   기존 클라이언트 객체는 직접 구현 객체를 생성, 연결, 실행하는 다양한 책임을 갖고 있었다.
   따라서 우리는 **구현 객체를 생성, 연결하는 책임**은 AppConfig가 담당하게 하고 클라이언트 객체는 **실행**만 하도록 관심사를 분리했다.

2. DIP (의존관계 역전 원칙) : 프로그래머는 "추상화에 의존해야지, 구체화에 의존하면 안된다" , 의존성 주입은 이 원칙을 따르는 방법 중 하나다. 
    할인 정책을 추가하고 적용하려 했더니, 클라이언트 코드(OrderService)도 변경해야 했다. DiscountPolicy 필드에 인터페이스를 추가헤 추상화 인터페이스에
    의존하는 것 같았지만 **결국은 클라이언트 코드에서 해당 필드에 구현체를 넣어주며 구체화 구현 클래스까지도 의존한 상태**였다.

    따라서 우리는 클라이언트 코드가 DiscountPolicy 필드에 구현체를 직접 넣어주지 않고,  
    AppConfig를 통해 객체를 생성하여 의존관계를 주입해 DIP원칙을 따르도록 변경했다.

3. OCP : 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
    
   다형성을 사용하고, 클라이언트가 DIP를 지킨다. 애플리케이션을 사용 영역과 구성 영역으로 나눠서
   AppConfig가 의존관계를 각 상황에 맞춰 클라이언트 코드에 주입해주므로 클라이언트 코드는 변경하지 않았다.

   즉 소프트웨어 요소를 새롭게 확장해도(할인 정책을 추가해도) 사용 영역의 클라이언트 코드는 변하지 않았으므로 OCP 원칙까지 지킬 수 있었다.

---

**IoC 컨테이너, DI 컨테이너 : 의존관계 주입 (Dependency Injection)**

AppConfig처럼 객체를 생성하고 관리하면서 의존관계를 연결해주는 것을 **IoC 컨테이너 or DI 컨테이너** 라고 한다.

---

**BeanFactory, ApplicationContext**

1. BeanFactory
   - 스프링 컨테이너의 최상위 인터페이스이다.
   - 스프링 빈을 관리하고 조회하는 역할을 담당한다.

2. ApplicationContext
   - BeanFactory의 기능을 모두 상속받아 제공한다.
   - 애플리케이션을 개발할 때는 빈의 관리 조회 기능말고도 필요한 부가기능들을 제공한다.
   - 부가기능
      1. MessageSource 
         한국에서 들어오면 한국어로, 영어권에서 들어오면 영어로 출력
      2. EnvironmentCapable
         로컬, 개발, 운영 등을 구분해서 처리
      3. ApplicationEventPublisher
         이벤트를 발행하고 구독하는 모델을 편리하게 지원
      4. ResourceLoader
         파일, 클래스 패스, 외부 등에서 리소스를 편리하게 조회

---

**스프링 빈 설정 메타 정보 - BeanDefinition**

`AnnotationConfigApplicationContext`는 `AnnotatedBeanDefinitionReader`를 사용해서
`@Configuration` 어노테이션이 있는 AppConfig 클래스 `AppConfig.class`에서 Bean의 메타정보를 읽고 `BeanDefinition`을 생성한다.

---

**컴포넌트 스캔 기본 대상**

컴포넌트 스캔은 `@Component` 뿐만 아니라 아래의 내용도 추가로 포함시킨다.

- `@Component` : 컴포넌트 스캔에서 사용
- `@Controller` : 스프링 MVC 컨트롤러에서 사용되며, 스프링은 MVC 컨트롤러로 인식한다.
- `@Service` : 스프링 비즈니스 로직에서 사용된다.
- `@Repository` : 스프링 데이터 접근계층에서 사용되며, 스프링 데이터 접근 계층으로 인삭하고, 데이터 계층의 예외를 스프링 예외로 변환해준다. 
이유는 벤더 종속성에 따라 Exception의 종류가 달라지기 때문에 Service 레이어의 설계가 흔들릴 수 있기 때문에 위처럼 예외를 치환해준다. 
- `@Configuration` : 스프링 설정 정보에서 사용된다. 또한 스프링이 설정 정보로 인식하여, 스프링 빈이 싱글톤을 유지하도록 추가 처리를 해준다.

위 어노테이션들에는  `@Component` 어노테이션이 포함되어있기 때문이다. 그리고 어노테이션에는 상속관계라는 개념이 없다.
따라서 어노테이션이 특정 어노테이션을 들고있는 것을 인식하는 것은 자바가 아닌 스프링이 지원하는 기능이다. 또한 위 어노테이션의 이름에 맞춰
부가 기능을 수행한다.

- Bean은 싱글톤 패턴으로 생성되기 때문에 네이밍이 중복될 경우 `ConflictingBeanDefinitionException` 이 터짐. 
- 하지만 자동 등록 빈과 수동 등록 빈의 네이밍이 같을 경우에는 Exception이 터지지 않고 수동 빈이 자동 빈을 오버라이딩한다.
- 따라서 요즘엔 스프링부트가 그냥 `Exception`을 터뜨린다.

---

**Field Injection, Constructor Injection**

필드 주입 방식은 사실 안티 패턴이다. 그 이유는 첫번째로 테스팅이 불가능하다. 내부 필드에 객체를 주입할 수 있는 방법이 스프링 컨테이너가 주입하는 방법밖에 없기 때문이다.
따라서 최근에서는 스프링을 포함한 DI 프레임워크 대부분이 생성자 주입을 권장한다.

그 이유는 대부분의 의존관계 주입은 한번 일어나고 나면 애플리케이션 종료 시점까지 의존관계를 변경할 일이 없고, 변해서도 안된다. 수정자 주입은 setter 메소드를  public으로
열어두어야 하기 때문에 누군가 실수로 변경할수도 있는 단점이 있다. 또한 변경하면 안되는 메서드를 열어두는 것은 좋은 설계가 아니다.

따라서 생성자 주입을 사용하는 것이 권장된다. 그 이유는 객체를 생성할 때 딱 1번 호출되며, 이후 호출되지 않으므로 불변하게 설계될 수 있다.

---

**자동, 수동의 올바른 실무 운영 기준**

스프링이 나오고 시간이 갈수록 점점 자동을 선호하는 추세다.물론 설정 정보를 기반으로 애플리케이션을 구성하는 부분과 실제 동작하는 부분을 명확하게 나누는 것이 이상적이지만,
개발자 입장에서는 번거로운 일이 커지고 유지보수가 힘들다. 그리고 자동 빈 등록으로도 OCP,DIP 를 지킬 수 있다.

기술 지원 객체는 수동으로 빈을 등록해서 명확하게 드러내고 나머지는 `@Controller, @Service, @Repository` 등은 자동 빈 등록을 이용하자.

--- 

**Bean Scope**

스프링 빈은 기본적으로 싱글톤 스코프로 생성된다. 이 외에 웹 관련 스코프는 아래와 같다.
- `request`:웹 요청이 들어오고 나갈때까지 유지
- `session`: 웹 세션이 생성되고ㅠ 종료될 때까지 유지
- `application`: 웹의 서블릿 컨텍스트와 같은 범위로 유지
- `websocket` : 웹 소켓과 동일한 생명주기를 갖는 스코프

---

**Singleton Bean vs PrototypeBean**

- 싱글톤 빈: 스트링 컨테이너 생성 시점에 생성되고, 초기화 메서드가 실행된다.
- 프로토타입 빈: 스프링 컨테이너에서 빈을 조회할 때 생성되고, 초기화 메서드도 실행된다.

싱글톤 빈은 스프링 컨테이너가 관리하기 때문에 스프링 컨테이너가 종료될 때 빈의 종료 메서드가 실행되지만, 프로토타입 빈은 스프링 컨테이너가 
생성과 의존관계 주입, 그리고 초기화까지만 관여하고 더는 관리하지 않는다. 따라서 프로토타입 빈은 스프링 컨테이너가 종료될 떄 `@PreDestory`와 같은 종료 메서드가 전혀 실행되지 않는다.