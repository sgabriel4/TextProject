<!-- VISTA PRINCIPAL DE UN USUARIO REGISTRADO -->
<!-- VARIABLES PARA CONTROLAR AVISOS -->
<p id="proyectoCreado" name="proyectoCreado" hidden>${proyectoCreado}</p>
<p id="proyectoEliminado" name="proyectoEliminado" hidden>${proyectoEliminado}</p>
<p id="packageCreado" name="packageCreado" hidden>${packageCreado}</p>
<p id="packageEliminado" name="packageEliminado" hidden>${packageEliminado}</p>
<p id="documentoSubido" name="documentoSubido" hidden>${documentoSubido}</p>
<p id="documentoEliminado" name="documentoEliminado" hidden>${documentoEliminado}</p>
<p id="documentoSubidoName" name="documentoSubidoName" hidden>${documentoSubidoName}</p>
<!-- FIN VARIABLES PARA CONTROLAR AVISOS -->

<div id="wrapper"> 
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-trash fa-fw"></i> <i class="fa fa-caret-down">
                    </i>
                </a>
                <!-- OPCION PARA ELIMINAR -->
                <ul class="dropdown-menu">
                    <li><div class="btn btn-danger " style="margin: 2" data-toggle="modal" data-target="#modalEliminarDocumento" 
                             name="deleteDocumentButton" id="deleteDocumentButton">Borrar Documento</div></li>
                    <li> <div class="btn btn-danger " style="margin: 2" data-toggle="modal" data-target="#modalEliminarProyecto" 
                              name="deleteProyectButton" id="deleteProyectButton">Borrar Proyecto</div></li>
                    <li><div class="btn btn-danger " style="margin: 2" data-toggle="modal" data-target="#modalEliminarPaquete" 
                             name="deletePackageButton" id="deletePackageButton">Borrar Paquete</div></li>
                </ul>
                <!-- FIN OPCION PARA ELIMINAR -->
            </li>
        </ul>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <div class="row">
                <div class="container">
                    <div class="col-md-2 alert alert-success">
                        <strong> Bienvenido, ${user.username}!</strong>
                    </div>
                    <div class="col-md-3">
                        <div class="btn btn-success" data-toggle="modal" data-target="#modalSubirArchivo" 
                             name="uploadButton" id="uploadButton"><span class="glyphicon glyphicon-paperclip"></span> Subir Documento</div>
                    </div>
                </div>
            </div>      
        </div>
        <p></p>
        <div class="navbar-default sidebar" role="navigation">
            <div class="btn btn-success float-left" style="margin: 2" data-toggle="modal" data-target="#modalCrearProyecto"><span class="glyphicon glyphicon-plus"></span> Crear proyecto</div>
            <div class="btn btn-success" style="margin: 2" data-toggle="modal" data-target="#modalCrearPaquete" id="paqueteButton"><span class="glyphicon glyphicon-folder-open"></span> Crear Paquete</div>
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <c:forEach items="${projects}" var="project">
                        <li>
                            <a id = "projButton${project.id}" href="#" onclick="{
                                        document.formPaquete.project_id.value =${project.id};
                                    }" data-toggle="modal" data-target="#myModal"><i class="fa fa-archive fa-fw"></i>${project.name}<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <c:forEach items="${project.packages}" var="pack">
                                    <li>
                                        <a id = "pakButton${pack.id}" href="#" onclick="{
                                                    document.formSubir.package_id.value =${pack.id};
                                                }"><i class="fa fa-folder-open fa-fw"></i>${pack.name}<span class="fa arrow"></span></a>
                                        <ul class="nav nav-third-level">
                                            <c:forEach items="${pack.documents}" var="document">
                                                <li>
                                                    <a id = "docButton/${document.id}" href="#" onclick="loadDoc('${document.fileName}', '${document.showName}')"><i class="fa fa-file fa-fw"></i>${document.showName}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>    
                </ul>
            </div>

        </div>
    </nav>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <!-- MUESTRA EL ARCHIVO SELECIONADO -->
                <h1 id="title" class="page-header">Texto del documento</h1>
            </div>
        </div>
        <div id="text" class="row">

        </div>
    </div>

</div>                

<!-- Modal Crear Proyecto-->
<div id="modalCrearProyecto" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Nuevo Proyecto</h4>
            </div>
            <div class="modal-body">
                <label for="text">Ingrese nombre del proyecto</label>
                <form action = "newProject" method = "post">
                    <input type = "text" name = "project_name" required/>
                    <input type = "submit" value = "Crear Proyecto" />
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
            </div>
        </div>

    </div>
</div>

<!--Modal Borrar Proyecto-->
<div id="modalEliminarProyecto" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content panel-danger">
            <div class="modal-header panel-heading">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Borrar Proyecto</h4>
            </div>
            <div class="modal-body">
                <label for="text">¿ESTA SEGURO DE ELIMINAR EL PROYECTO?</label>
                </br>
                ESTO BORRARA LOS PAQUETES Y ARCHIVOS ASOCIADOS AL PROYECTO:
                <form name="formDeleteProyect" action = "deleteProject" method = "post">
                    </br>
                    <h2 class="text-uppercase text-center" id="projName"></h2>
                    <input type = "hidden" name = "project_id1" id="projID1"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">CANCELAR</button>
                <input class="btn btn-danger"type = "submit" value = "BORRAR PROYECTO" />
                </form>
            </div>
        </div>

    </div>
</div>

<!-- Modal Crear Paquete-->
<div id="modalCrearPaquete" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Nuevo Paquete</h4>
            </div>
            <div class="modal-body">
                <label for="text">Ingrese nombre del paquete</label>
                <form name = "formPaquete" action = "newPackage" method = "post">
                    <input type = "text" name = "package_name" required/>
                    <input type="hidden" name="project_id" id="projID"/>
                    <input type = "submit" value = "Crear Paquete" />
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
            </div>
        </div>

    </div>
</div>

<!--Modal Borrar Paquete-->
<div id="modalEliminarPaquete" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content panel-danger">
            <div class="modal-header panel-heading">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">BORRAR PAQUETE</h4>
            </div>
            <div class="modal-body">
                <label for="text">¿ESTA SEGURO DE ELIMINAR EL PAQUETE?</label>
                </br>
                ESTO BORRARA LOS ARCHIVOS ASOCIADOS AL PAQUETE:
                <form name="formDeletePackage" action = "deletePackage" method = "post">
                    </br>
                    <h2 class="text-uppercase text-center" id="packageName"></h2>
                    <input type = "hidden" name = "package_id1" id="packageID1"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">CANCELAR</button>
                <input class="btn btn-danger"type = "submit" value = "BORRAR PAQUETE" />
                </form>
            </div>
        </div>

    </div>
</div>

<!-- Modal Subir Archivo-->
<div id="modalSubirArchivo" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Subir Archivo:</h4>
            </div>
            <div class="modal-body">
                <label for="text">Selecciona el archivo a extraer texto (tamaño maximo: 5MB)</label>
                <form name = "formSubir" action = "upload" method = "post" enctype="multipart/form-data">
                    <input type="hidden" name="package_id" id="packID"/>
                    <input type = "file" name = "file" size = "5" accept=".pdf, .doc, .docx, .txt, .html" required/></br>
                    <input type = "submit" class="btn btn-success" value = "Subir Archivo" />
                </form>
                <p></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
            </div>
        </div>

    </div>
</div>

<!--Modal Borrar Paquete-->
<div id="modalEliminarDocumento" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content panel-danger">
            <div class="modal-header panel-heading">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Borrar Documento</h4>
            </div>
            <div class="modal-body">
                <label for="text">¿Esta seguro que desea borrar el documento?</label>
                </br>
                SI ACEPTA, EL DOCUMENTO NO SE PODRA RECUPERAR.
                <form action = "deleteDocument" method = "post">
                    </br>
                    <h2 class="text-uppercase text-center" id="documentName"></h2>
                    <input type = "hidden" name = "document_id1" id="documentID1"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">CANCELAR</button>
                <input class="btn btn-danger"type = "submit" value = "BORRAR DOCUMENTO" />
                </form>
            </div>
        </div>

    </div>
</div>

<!-- Modal Aviso Correcto -->
<div class="modal fade" id="modalGood" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content panel-success">
            <div class="modal-header panel-heading">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Aviso</h4>
            </div>
            <div class="modal-body">
                <p id="textoGood"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">Entendido</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal Aviso Error -->
<div class="modal fade" id="modalError" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content panel-danger">
            <div class="modal-header panel-heading">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">ERROR</h4>
            </div>
            <div class="modal-body">
                <p id="textoError"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">Entendido</button>
            </div>
        </div>
    </div>
</div>


<script>
    function loadDoc(fileName, showName) {
        var xhttp = new XMLHttpRequest();
        console.log(fileName + " " + showName);
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                document.getElementById("text").innerHTML =
                        this.responseText;
                document.getElementById("title").innerHTML =
                        showName;
            }
        };
        xhttp.open("GET", "getArchivo?file=" + fileName, true);
        xhttp.send();
    }
    $(document).ready(function () {
        document.getElementById("textconverter").href = "dashboard";
        document.getElementById("uploadButton").style.visibility = "hidden";
        document.getElementById("deleteProyectButton").style.visibility = "hidden";
        document.getElementById("deletePackageButton").style.visibility = "hidden";
        document.getElementById("deleteDocumentButton").style.visibility = "hidden";
        document.getElementById("paqueteButton").style.visibility = "hidden";
        document.getElementById("botonDesconectarse").style.visibility = "visible";
        if ($('#proyectoCreado').text() === "valido") {
            document.getElementById("textoGood").textContent = "Proyecto Creado Correctamente.";
            $("#modalGood").modal();
        }
        if ($('#proyectoCreado').text() === "invalido") {
            document.getElementById("textoError").textContent = "Error al crear proyecto, puede que el nombre del proyecto ya exista";
            $("#modalError").modal();
        }
        if ($('#packageCreado').text() === "valido") {
            document.getElementById("textoGood").textContent = "Package Creado Correctamente.";
            $("#modalGood").modal();
        }
        if ($('#packageCreado').text() === "invalido") {
            document.getElementById("textoError").textContent = "Error al crear package";
            $("#modalError").modal();
        }
        if ($('#documentoSubido').text() === "valido") {
            document.getElementById("textoGood").textContent = "Documento " + $('#documentoSubidoName').text() + " Subido Correctamente.";
            $("#modalGood").modal();
        }
        if ($('#documentoSubido').text() === "invalido") {
            document.getElementById("textoError").textContent = "Error al subir el documento.";
            $("#modalError").modal();
        }
        if ($('#proyectoEliminado').text() === "valido") {
            document.getElementById("textoGood").textContent = "Proyecto Eliminado Correctamente.";
            $("#modalGood").modal();
        }
        if ($('#proyectoEliminado').text() === "invalido") {
            document.getElementById("textoError").textContent = "Error al eliminar el proyecto.";
            $("#modalError").modal();
        }
        if ($('#packageEliminado').text() === "valido") {
            document.getElementById("textoGood").textContent = "Package Eliminado Correctamente.";
            $("#modalGood").modal();
        }
        if ($('#packageEliminado').text() === "invalido") {
            document.getElementById("textoError").textContent = "Error al eliminar el package.";
            $("#modalError").modal();
        }
        if ($('#documentoEliminado').text() === "valido") {
            document.getElementById("textoGood").textContent = "Documento Eliminado Correctamente.";
            $("#modalGood").modal();
        }
        if ($('#documentoEliminado').text() === "invalido") {
            document.getElementById("textoError").textContent = "Error al eliminar el documento.";
            $("#modalError").modal();
        }
    });


    $("a[id*='pakButton']").click(function () {
        document.getElementById("deleteDocumentButton").style.visibility = "hidden";
        document.getElementById("uploadButton").style.visibility = "visible";
        document.getElementById("deletePackageButton").style.visibility = "visible";
        document.getElementById("packageID1").value = document.getElementById("packID").value;
        document.getElementById("packageName").textContent = this.text;
    });

    $("a[id*='projButton']").click(function () {
        document.getElementById("uploadButton").style.visibility = "hidden";
        document.getElementById("deletePackageButton").style.visibility = "hidden";
        document.getElementById("deleteDocumentButton").style.visibility = "hidden";
        document.getElementById("paqueteButton").style.visibility = "visible";
        document.getElementById("deleteProyectButton").style.visibility = "visible";
        document.getElementById("projID1").value = document.getElementById("projID").value;
        document.getElementById("projName").textContent = this.text;
    });

    $("a[id*='docButton']").click(function () {
        var idString = this.id;
        var id = idString.split("/");
        console.log(id[1]);
        document.getElementById("deleteDocumentButton").style.visibility = "visible";
        document.getElementById("documentID1").value = id[1];
        document.getElementById("documentName").textContent = this.text;
    });

</script>
