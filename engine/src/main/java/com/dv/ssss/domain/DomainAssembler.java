package com.dv.ssss.domain;

import static org.qi4j.api.common.Visibility.application;
import static org.qi4j.api.common.Visibility.layer;

import com.dv.ssss.Engine;
import com.dv.ssss.domain.age.AgeRepository;
import com.dv.ssss.domain.game.Game;
import com.dv.ssss.domain.game.GameFactory;
import com.dv.ssss.domain.game.GameRepository;
import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.people.PersonEntity;
import com.dv.ssss.domain.people.PersonFactory;
import com.dv.ssss.domain.people.PersonnelRepository;
import com.dv.ssss.inf.LayerAssembler;
import com.dv.ssss.personnel.PersonnelService;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;

public class DomainAssembler implements LayerAssembler {

    @Override
    public LayerAssembly assemble(ApplicationAssembly assembly) {

        LayerAssembly domain = assembly.layer("domain");

        game(domain);

        ModuleAssembly domainModules = domain.module("root");
        domainModules.entities(
                PersonEntity.class
        );

        domainModules.services(
                PersonnelService.class
        ).visibleIn(application);

        domainModules.services(
                AgeRepository.class,
                PersonnelRepository.class
        );

        domainModules.services(
                PersonFactory.class
        ).visibleIn(layer);

        domainModules.services(Engine.class)
                     .visibleIn(application);
        return domain;
    }

    private void game(LayerAssembly domain) {

        ModuleAssembly module = domain.module("game");
        module.services(
                GameService.class
        ).visibleIn(application);

        module.services(
                GameFactory.class,
                GameRepository.class
        );

        module.services(
                DataBootstrap.class
        );

        module.entities(Game.class);
    }
}