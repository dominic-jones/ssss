package com.dv.ssss.ui.personnel;

public class TransferClickedEvent {

    private PersonDto selectedPerson;

    public TransferClickedEvent(PersonDto selectedPerson) {

        this.selectedPerson = selectedPerson;
    }

    public PersonDto getSelectedPerson() {

        return selectedPerson;
    }
}
