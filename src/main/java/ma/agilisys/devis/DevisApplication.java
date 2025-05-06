package ma.agilisys.devis;

import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.models.Contact;
import ma.agilisys.devis.repositories.ClientRepository;
import ma.agilisys.devis.repositories.ContactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DevisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevisApplication.class, args);
    }

    //    @Bean
    CommandLineRunner initDatabase(ClientRepository clientRepository, ContactRepository contactRepository) {
        return args -> {
            // Création d'un client
            Client client = new Client();
            client.setNom("ABC Corporation");
            client.setIce("ICE123456789");
            client.setAdresse("123 Rue Principale");
            client.setVille("Casablanca");
            client.setPays("Maroc");
            client.setDateCreation(ZonedDateTime.now());

            // Sauvegarde du client (génère l'ID)
            clientRepository.save(client);

            List<Contact> contacts = new ArrayList<>();
            // Création des contacts associés
            Contact contact1 = new Contact();
            contact1.setNom("Mohamed Ali");
            contact1.setEmail("mohamed.ali@abccorp.com");
            contact1.setTelephone("+212600123456");
            contact1.setFonction("Directeur Commercial");
            contact1.setClient(client); // Lien avec le client
            contact1.setDateCreation(ZonedDateTime.now());

            Contact contact2 = new Contact();
            contact2.setNom("Fatima Zahra");
            contact2.setEmail("fatima.zahra@abccorp.com");
            contact2.setTelephone("+212600654321");
            contact2.setFonction("Responsable Logistique");
            contact2.setClient(client); // Lien avec le client
            contact2.setDateCreation(ZonedDateTime.now());
            contacts.add(contact1);
            contacts.add(contact2);
            // Sauvegarde des contacts
            contactRepository.saveAll(contacts);
        };
    }
}
