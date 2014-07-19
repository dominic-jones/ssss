package com.dv.ssss.domain;

import com.dv.ssss.domain.faction.FactionEntity;
import com.dv.ssss.domain.faction.FactionFactory;
import com.dv.ssss.domain.faction.FactionRepository;
import com.dv.ssss.domain.game.GameEntity;
import com.dv.ssss.domain.game.GameFactory;
import com.dv.ssss.domain.game.GameRepository;
import com.dv.ssss.domain.game.GameService;
import com.dv.ssss.domain.people.PersonEntity;
import com.dv.ssss.domain.people.PersonFactory;
import com.dv.ssss.domain.people.PersonnelRepository;
import com.dv.ssss.inf.LayerAssembler;
import com.dv.ssss.personnel.PersonnelService;
import org.qi4j.api.common.Visibility;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;

import static org.qi4j.api.common.Visibility.application;

public class DomainAssembler implements LayerAssembler {

    @Override
    public LayerAssembly assemble(ApplicationAssembly assembly) {

        LayerAssembly domain = assembly.layer("domain");

        game(domain);
        person(domain);

        return domain;
    }

    private void game(LayerAssembly assembly) {

        ModuleAssembly module = assembly.module("game");
        module.services(
                GameService.class
        ).visibleIn(application);

        module.services(
                GameFactory.class,
                GameRepository.class
        );

        module.entities(
                GameEntity.class
        );
    }

    private void person(LayerAssembly layer) {

        ModuleAssembly module = layer.module("person");

        module.services(
                PersonnelService.class
        ).visibleIn(application);

        module.services(
                FactionFactory.class,
                PersonFactory.class
        ).visibleIn(Visibility.layer);

        module.services(
                FactionRepository.class,
                PersonnelRepository.class
        );

        module.entities(
                FactionEntity.class,
                PersonEntity.class
        );
    }
}