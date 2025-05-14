<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Devis ${devis.numero}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            line-height: 1.5;
        }

        .devis-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        .devis-table td {
            border: 1px solid #000;
            padding: 5px;
            vertical-align: top;
        }

        .bold {
            font-weight: bold;
        }

        .section {
            margin: 20px 0;
        }

        .image-container {
            text-align: center;
            margin: 15px 0;
        }

        .image-container img {
            width: 6.69in;
            height: auto;
        }

        .signature {
            text-align: right;
            margin-top: 50px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 15px 0;
        }

        table, th, td {
            border: 1px solid #000;
        }

        th, td {
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
<!-- En-tête du devis -->
<table class="devis-table">
    <tr>
        <td style="width: 30%;">
            <div class="bold">Numéro du devis</div>
            <div>${devis.numero}</div>
            <div class="bold">Date devis</div>
            <#--            <div><#if devis.dateCreation??>${devis.dateCreation?string("dd/MM/yyyy")}<#else>N/A</#if></div>-->
            <div class="bold">Montant du devis</div>
            <div>${devis.totalHt} HT</div>
        </td>
        <td style="width: 25%;">
            <div class="bold">Client</div>
            <div>${devis.client.nom}</div>
        </td>
        <td style="width: 45%; text-align: right;">
            <div>LOGO CLIENT</div>
            <div>ICE: ${devis.client.ice}</div>
        </td>
    </tr>
</table>

<!-- Section Prestation -->
<div class="section">
    <div class="bold">Prestation</div>
    <p class="bold">Lorem Ipsum</p>
</div>

<!-- Section Périmètre Fonctionnel -->
<div class="section">
    <div class="bold">Périmètre Fonctionnel</div>
    <p>Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le
        Lorem Ipsum est le faux texte standard de l'imprimerie depuis les années 1500, quand un imprimeur anonyme
        assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte.</p>
</div>

<!-- Section Standard Lorem Ipsum -->
<div class="section">
    <div class="bold">Le passage de Lorem Ipsum standard, utilisé depuis 1500</div>
    <p class="bold">"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
        et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
        commodo consequat."</p>
</div>

<!-- Section Ciceron -->
<div class="section">
    <div class="bold">Section 1.10.32 du "De Finibus Bonorum et Malorum" de Ciceron (45 av. J.-C.)</div>
    <p>"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem
        aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo."</p>
</div>

<!-- Section Traduction -->
<div class="section">
    <div class="bold">Traduction de H. Rackham (1914)</div>
    <p>"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I
        will give you a complete account of the system."</p>
</div>

<!-- Section Planning -->
<div class="section">
    <div class="bold">Planning</div>
    <div class="image-container">
        <img src="classpath:/templates/images/planning.jpg" alt="Planning"/>
    </div>
    <p>que survivre cinq siècles, mais s'est aussi adapté à la bureautique informatique, sans que son contenu n'en soit
        modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages
        du Lorem Ipsum.</p>
</div>

<!-- Section Architecture Fonctionnelle -->
<div class="section">
    <div class="bold">Architecture Fonctionnelle</div>
    <div class="image-container">
        <img src="classpath:/templates/images/architecture.png" alt="Architecture Fonctionnelle"/>
    </div>
    <p>que survivre cinq siècles, mais s'est aussi adapté à la bureautique informatique, sans que son contenu n'en soit
        modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages
        du Lorem Ipsum.</p>
</div>

<!-- Section Estimation de charge -->
<div class="section">
    <div class="bold">Estimation de charge</div>
    <table>
        <tr>
            <td style="height: 100px;"></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</div>

<!-- Section Offre financière et Conditions -->
<div class="section">
    <div class="bold">Offre financière et Conditions</div>
    <table>
        <tr>
            <td style="height: 150px;"></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</div>

<!-- Signature -->
<div class="signature">
    <p>Le ${.now?string("dd/MM/yyyy")}</p>
</div>
</body>
</html>