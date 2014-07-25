package com.dv.ssss.ui.personnel;

public class ChoosePlayerCommand {

    private PersonDto selectedPerson;

    public ChoosePlayerCommand(PersonDto selectedPerson) {

        this.selectedPerson = selectedPerson;
    }

    public PersonDto getSelectedPerson() {

        return selectedPerson;
    }
}
