<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Devis ${devis.numero}</title>
    <style>
        /* Reset and base styles */
        body, html {
            margin: 0;
            padding: 0;
            font-family: "Arial", sans-serif;
            font-size: 11pt;
            line-height: 1.5;
            color: #000;
        }

        /* Page setup */
        @page {
            size: A4;
            margin: 0;
        }

        .page {
            width: 210mm;
            height: 297mm;
            position: relative;
            padding: 15mm;
            box-sizing: border-box;
            overflow: hidden;
            page-break-after: always;
        }

        /* Header styles */
        .header-container {
            position: relative;
            width: 100%;
            margin-bottom: 20px;
            padding: 0;
        }

        .logo-container {
            display: inline-block;
            vertical-align: top;
        }

        .logo-container img {
            height: 80px;
            width: auto;
        }

        .info-container {
            position: absolute;
            top: 0;
            right: 0;
            text-align: right;
            font-size: 12px;
        }

        .info-line {
            line-height: 1.4;
            margin: 0;
            padding: 0;
            white-space: nowrap;
        }

        /* Devis header */

        .devis-info-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 5px;
            font-size: 10pt;
        }

        .devis-info-table td {
            padding: 5px 8px;
            vertical-align: top;
            border: none;
        }

        .devis-info-table .label {
            font-weight: bold;
            width: 20%;
        }

        .client-ice {
            text-align: right;
            font-size: 10pt;
            margin-bottom: 20px;
        }

        /* Content sections */
        .section-title {
            font-size: 14pt;
            margin: 10px 0;
            padding-bottom: 5px;
            border-bottom: 1px solid #ddd;
            color: #17a2b8;
        }

        /* Container spécifique pour les images */
        .image-fixed-container {
            width: 180mm;
            height: 90mm;
            margin: 10px auto;
            display: flex;
            justify-content: center;
            align-items: center;
            overflow: hidden;
            page-break-inside: avoid;
        }

        .image-fixed-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            object-position: center;
        }

        /* Tables */
        .data-table {
            width: 100%;
            border-collapse: collapse;
            margin: 15px 0;
            font-size: 10pt;
            page-break-inside: avoid;
        }

        .data-table th, .data-table td {
            border: 1px solid #000;
            padding: 8px;
            text-align: left;
        }

        .data-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        /* Footer */
        .footer {
            position: absolute;
            bottom: 10mm;
            left: 15mm;
            right: 15mm;
            padding: 5px 0;
            font-size: 7pt;
            text-align: center;
            border-top: 1px solid green;
            width: calc(100% - 30mm);
            line-height: 1.2;
            margin-top: 10mm; /* Nouvelle marge pour séparer du contenu */
            color: #003366;
        }

        /* Contenu principal avec marge protégeant le footer */
        .main-content {
            padding-bottom: 25mm; /* Augmentation de l'espace réservé pour le footer */
            margin-bottom: 10mm; /* Espace supplémentaire avant le footer */
            height: calc(100% - 25mm);
        }

        .highlight {
            background-color: #ffff00;
            display: inline-block;
        }

        /* Utility classes */
        .bold-italic {
            font-weight: bold;
            font-style: italic;
        }

        .text-right {
            text-align: right;
            margin-top: 30px;
        }

        .bold {
            font-weight: bold;
        }
    </style>
</head>
<body>

<!-- Page 1 -->
<div class="page">
    <div class="header-container">
        <div class="logo-container">
            <img src="classpath:/static/images/logo.png" alt="Logo Agilisys"/>
        </div>
        <div class="info-container">
            <div class="info-line">ICE: 001568437000073</div>
            <div class="info-line">www.agilisys.ma</div>
            <div class="info-line">ayoubelabbassi@agilisys.ma</div>
            <div class="info-line">0619826933</div>
        </div>
    </div>

    <div class="main-content">
        <div>
            <div class="section-title">Prestation</div>
            <div class="client-ice">
                LOGO client
            </div>
            <table class="devis-info-table">
                <tr>
                    <td class="label">Numéro du devis</td>
                    <td>${devis.numero}</td>
                    <td class="label">Client</td>
                </tr>
                <tr>
                    <td class="label">Date devis</td>
                    <#--                    <td><#if devis.dateCreation??>${devis.dateCreation?string('dd/MM/yyyy')}<#else>N/A</#if></td>-->
                    <td></td>
                </tr>
                <tr>
                    <td class="label">Montant du devis</td>
                    <td>${devis.totalHt} HT</td>
                    <td></td>
                </tr>
            </table>

            <div class="client-ice">
                ICE : 0016514141000019
            </div>
        </div>

        <div class="section-title">Prestation</div>
        <p class="bold-italic">Lorem Ipsum</p>

        <div class="section-title">Périmètre Fonctionnel</div>
        <p>
            Le <span class="bold-italic">Lorem Ipsum</span> est simplement du faux texte employé dans la composition et
            la mise
            en page avant impression. Le Lorem Ipsum est le faux texte standard de l'imprimerie depuis les années 1500,
            quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de
            polices de texte. Il n'a pas fait que survivre cinq siècles, mais s'est aussi adapté à la bureautique
            informatique, sans que son contenu n'en soit modifié. Il a été popularisé dans les années 1960 grâce à
            la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son
            inclusion dans des applications de mise en page de texte, comme Aldus PageMaker.
        </p>
        <p>Le passage de Lorem Ipsum standard, utilisé depuis 1500</p>
        <p class="bold">"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
            incididunt ut
            labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
            laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
            voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
            proident, sunt in culpa qui officia deserunt mollit anim id est laborum."</p>
        <p>
            <span class="highlight">Section 1.10.32 du "De Finibus Bonorum et Malorum" de Ciceron (45 av. J.-C.)</span>
        </p>
        <p>
            "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium,
            totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt
            explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia
            consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui
        </p>
    </div>

    <div class="footer">
        AGILISYS SARL AU Capital de 100 000.00 DH | ICE : 001568437000073 | Patente : 25728214 | IF : 1372126 | RC :
        105883 | CNSS : 4418114 | Tél : 0619826933 | Adresse : 15 avenue ALABTAL appartement 4, 4ème étage Rabat Maroc
    </div>
</div>

<!-- Page 2 -->
<div class="page">
    <div class="main-content">
        <p> dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora
            incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis
            nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur?
            Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur,
            vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
        </p>
        <p>Traduction de H. Rackham (1914)
            "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born
            and I will give you a complete account of the system, and expound the actual teachings of the great </p>
        <p>
            explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure
            itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally
            encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or
            desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which
            toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes
            laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault
            with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a
            pain that produces no resultant pleasure?"
        </p>

        <div class="section-title">Planning</div>
        <div class="image-fixed-container">
            <img src="classpath:/static/images/planning.jpg" alt="Planning de projet"/>
        </div>
        <p>
            que survivre cinq siècles, mais s'est aussi adapté à la bureautique informatique, sans que son contenu
            n'en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset
            contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications
            de mise en page de texte, comme Aldus PageMaker
        </p>
    </div>
    <div class="section-title">Architecture</div>
    <div class="footer">
        AGILISYS SARL AU Capital de 100 000.00 DH | ICE : 001651414100019 | Patente : 25728214 | IF : 1372126 | RC :
        105883 | CNSS : 4418114 | Tél : 0619826933 | Adresse : 15 avenue ALABTAL appartement 4, 4ème étage Rabat Maroc
    </div>
</div>

<!-- Page 3 -->
<div class="page">
    <div class="main-content">
        <div class="image-fixed-container">
            <img src="classpath:/static/images/architecture.png" alt="Architecture fonctionnelle"/>
        </div>
        <p>
            que survivre cinq siècles, mais s'est aussi adapté à la bureautique informatique, sans que son contenu
            n'en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset
            contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications
            de mise en page de texte, comme Aldus PageMaker
        </p>
        <div class="section-title">Estimation de charge</div>
        <table class="data-table">
            <thead>
            </thead>
            <tbody>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <div class="section-title">Offre financière et Conditions</div>
        <table class="data-table">
            <thead>
            </thead>
            <tbody>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <div class="text-right bold">
            Le 24/03/2023
        </div>
    </div>

    <div class="footer">
        AGILISYS SARL AU Capital de 100 000.00 DH | ICE : 001568437000073 | Patente : 25728214 | IF : 1372126 | RC :
        105883 | CNSS : 4418114 | Tél : 0619826933 | Adresse : 15 avenue ALABTAL appartement 4, 4ème étage Rabat Maroc
    </div>
</div>

<!-- Page 4 -->
<div class="page">
    <div class="main-content">
    </div>

    <div class="footer">
        AGILISYS SARL AU Capital de 100 000.00 DH | ICE : 001568437000073 | Patente : 25728214 | IF : 1372126 | RC :
        105883 | CNSS : 4418114 | Tél : 0619826933 | Adresse : 15 avenue ALABTAL appartement 4, 4ème étage Rabat Maroc
    </div>
</div>

</body>
</html>