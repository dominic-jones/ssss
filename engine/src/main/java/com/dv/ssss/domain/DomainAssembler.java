package com.dv.ssss.domain;

import static org.qi4j.api.common.Visibility.application;

import com.dv.ssss.domain.faction.FactionEntity;
import com.dv.ssss.domain.faction.FactionFactory;
import com.dv.ssss.domain.faction.FactionRepository;
import com.dv.ssss.domain.game.GameEntity;
import com.dv.ssss.domain.game.GameFactory;
import com.dv.ssss.domain.game.GameRepository;
import com.dv.ssss.domain.people.PersonEntity;
import com.dv.ssss.domain.people.PersonFactory;
import com.dv.ssss.domain.people.PersonnelRepository;
import com.dv.ssss.inf.LayerAssembler;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;

public class DomainAssembler implements LayerAssembler {

    @Override
    public LayerAssembly assemble(ApplicationAssembly assembly) {

        LayerAssembly domain = assembly.layer("domain");

        module(domain);

        return domain;
    }

    private void module(LayerAssembly layerAssembly) {

        ModuleAssembly moduleAssembly = layerAssembly.module("domain");

        moduleAssembly.services(
                FactionFactory.class,
                GameFactory.class,
                PersonFactory.class
        ).visibleIn(application);

        moduleAssembly.services(
                FactionRepository.class,
                GameRepository.class,
                PersonnelRepository.class
        ).visibleIn(application);

        moduleAssembly.entities(
                FactionEntity.class,
                GameEntity.class,
                PersonEntity.class
        );
    }
}