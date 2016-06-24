package minesweeper.controller.impl;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import minesweeper.MinesweeperModule;

/**
 * Created by dohahn on 16.06.2016.
 * Dependency Injector for MainControllerActor
 */
public class DependencyInjectorMainController implements IndirectActorProducer {

    @Override
    public Actor produce() {
        Injector injector = Guice.createInjector(new MinesweeperModule());
        return injector.getInstance(MainController.class);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return MainController.class;
    }
}
