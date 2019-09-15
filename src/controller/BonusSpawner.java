package controller;

import java.util.concurrent.TimeUnit;

/**
 * This class extending thread is in charged of selecting randomly bonuses, and making them spawn.
 * it also implements a singleton to allow one and only instance of it to exist.
 */
public final class BonusSpawner extends Thread {
    private static BonusSpawner SINGLETON = null;
    private final SpongebobGameController controller;
    
    
    private BonusSpawner(final SpongebobGameController controller) {
        super();
        this.controller = controller;
    }
    
    /**this is the SINGLETON method that allows one and only instance of this class.
     * @param controller  SpongebobGameController
     * @return the instance of this class
     */
    public static synchronized BonusSpawner getBonusSpawner(final SpongebobGameController controller) {
        if (SINGLETON == null) {
            SINGLETON = new BonusSpawner(controller);
        }
        return SINGLETON;
    }
 
  
  @Override
  public void run() {
    while (true) {
      try {
        TimeUnit.MILLISECONDS.sleep(this.controller.getModel().getBonusRate());
        this.randomChoice();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
   
    private void randomChoice() {
        final int bonusSelector = (int) (Math.random() * 4);
        switch (bonusSelector) {
        case 0:
            final Thread  mrKrab = new Thread(new MrKrabManager(this.controller));
            mrKrab.start();
            break;
        case 1:
            final Thread patrick = new Thread(new PatrickManager(this.controller));
            patrick.start();
            break;
        case 2:
            final Thread krabbyPatty = new Thread(new KrabbyPattyManager(this.controller));
            krabbyPatty.start();
            break;
        case 3:
            final Thread gary = new Thread(new GaryManager(this.controller));
            gary.start();
            break;
        default: 
            throw new IllegalStateException();
        }
    }
  }

