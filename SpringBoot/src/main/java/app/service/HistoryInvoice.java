/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.InvoiceDao;
import app.dao.PartnerDao;
import app.dao.PersonDao;
import app.dao.GuestDao;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.GuestDto;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryInvoice {

    private final InvoiceDao invoiceDao = new InvoiceDao();
    private final PersonDao personDao = new PersonDao();
    private final PartnerDao partnerDao = new PartnerDao();
    private final GuestDao guestDao = new GuestDao();
    
    public void showClubHistory() throws Exception {
    ArrayList<InvoiceDto> invoices = invoiceDao.listClubInvoices();
    if (invoices.isEmpty()) {
        throw new Exception("No hay historial de facturación.");
    }
    System.out.println("Historial completo de facturación:");
    for (InvoiceDto invoice : invoices) {
        PersonDto person = personDao.findByPersonId(invoice.getPersonId()); 
        PartnerDto partner = partnerDao.findByPartnerId(invoice.getPartnerId()); 
        System.out.println("Factura ID: " + invoice.getId() + ", Socio: " + person.getName() + ", Fecha: " + invoice.getCreationDate() + ", Monto: $" + invoice.getAmount());
    }
}


    // Historial de facturas de un socio
    public void showPartnerHistory(PartnerDto partnerDto) throws Exception {
        ArrayList<InvoiceDto> invoices = invoiceDao.listInvoicesByPartnerId(partnerDto.getId());
        if (invoices.isEmpty()) {
            throw new Exception("No hay historial de facturación para el socio " + partnerDto.getName());
        }
        System.out.println("Historial de facturas para el socio: " + partnerDto.getName());
        for (InvoiceDto invoice : invoices) {
            PersonDto person = personDao.findByPersonId(invoice.getPersonId());
            System.out.println("Factura ID: " + invoice.getId() + ", Fecha: " + invoice.getCreationDate() + ", Monto: $" + invoice.getAmount() + ", Responsable: " + person.getName());
        }
    }

    // Historial de facturas de un invitado
    public void showGuestHistory(GuestDto guestDto) throws Exception {
        PartnerDto partnerDto = partnerDao.findByGuestPartnerId(guestDto);
        if (partnerDto == null) {
            throw new Exception("No se encontró al socio responsable para este invitado.");
        }

        ArrayList<InvoiceDto> invoices = invoiceDao.listInvoicesByGuestId(guestDto.getId());
        if (invoices.isEmpty()) {
            throw new Exception("No hay historial de facturación para el invitado " + guestDto.getUserId());
        }

        System.out.println("Historial de facturas para el invitado: " + guestDto.getUserId());
        for (InvoiceDto invoice : invoices) {
            System.out.println("Factura ID: " + invoice.getId() + ", Fecha: " + invoice.getCreationDate() + ", Monto: $" + invoice.getAmount());
        }
    }
}


