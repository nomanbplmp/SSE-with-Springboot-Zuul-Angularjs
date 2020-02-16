# AngularJs Springboot Zull SSE
#Sample application to create SSE through Zuul proxy.

** Components **

1. Web client build with Html and AngularJs.
   On load client subscribe to Server sent events at endpoint  /ticks.
   
   
 <code>  const eventSource = new EventSource('http://localhost:8089/ticks'); 
  eventSource.onmessage=function(event) {
	  const msg = JSON.parse(event.data);
	  $scope.tick=msg.id;
	  $scope.$apply();
	}; </code>
  
  
 Received Event Data is upused to update $scope.tick.  Ticks are displayed in html as mentioned below
 <code>"{{tick}}</code> 
 
 2. **Java Mircroservice Application**
 
    This is a simple java service generating  SSE.
    
   
```    @Controller
public class SSEController {

  private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();
  
  
  @GetMapping("/ticks")
  public SseEmitter handle(HttpServletResponse response) {
    response.setHeader("Cache-Control", "no-store");

    SseEmitter emitter = new SseEmitter();
    // SseEmitter emitter = new SseEmitter(180_000L);

    this.emitters.add(emitter);

    emitter.onCompletion(() -> this.emitters.remove(emitter));
    emitter.onTimeout(() -> this.emitters.remove(emitter));

    new Thread(()-> {
    	while(true) {
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		//System.out.println("calling tick");
    		onTick(new TickInfo("tick" + UUID.randomUUID()));
    	}
    }).start();
    
    return emitter;
  }

  @EventListener
  public void onTick(TickInfo tickInfo) {
    List<SseEmitter> deadEmitters = new ArrayList<>();
    this.emitters.forEach(emitter -> {
      try {
        emitter.send(tickInfo);

        // close connnection, browser automatically reconnects
        // emitter.complete();

        // SseEventBuilder builder = SseEmitter.event().name("second").data("1");
        // SseEventBuilder builder =
        // SseEmitter.event().reconnectTime(10_000L).data(memoryInfo).id("1");
        // emitter.send(builder);
      }
      catch (Exception e) {
        deadEmitters.add(emitter);
      }
    });

    this.emitters.removeAll(deadEmitters);
  }
}
```



3. Gatway application using ZUUL proxy

 
