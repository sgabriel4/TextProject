<!-- VISTA LOGIN -->
<!-- VARIABLES PARA CONTROLAR AVISOS -->
<p id="verificar" name="verificar" hidden>${login}</p>
<p id="logueado" name="logueado" hidden>${logueado}</p>
<p id="registrar" name="registrar" hidden>${registrar}</p>
<p id="registroValido" name="registroValido" hidden>${registroValido}</p>
<!-- FIN VARIABLES PARA CONTROLAR AVISOS -->


<div class = "container">
    <div class="panel panel-default">
        <div class="panel-body">
            <form action="login" method="post" name="Login_Form" class="form-signin">       
                <h3 class="form-signin-heading" align="center">Ingresar al panel</h3>
                <hr class="colorgraph"><br>
                <input type="text" class="form-control" name="username" placeholder="Usuario" required="" autofocus="" />
                <p></p>
                <input type="password" class="form-control" name="password" placeholder="Contraseña" required=""/>     		  
                <p></p>
                <button class="btn btn-lg btn-success btn-block"  name="Submit" value="Login" type="Submit">Ingresar</button>  			
            </form>
        </div>
    </div>

    <p></p>
    <p class='text-center'>¿No estas registrado?. Registrate <a href="register">Aqui</a></p>
</div>


<!-- Modal Registro-->
<div id="modalRegistro" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content panel-info">
            <div class="modal-header panel-heading">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Registro de Usuario</h4>
            </div>
            <div class="modal-body">
                <form action="saveRegister" method="post">
                    <div class="form-group">
                        <label for="text">Ingrese Nombre de Usuario:</label>
                        <input type="text" class="form-control" name="username" maxlength="15">
                        <p></p>
                        <label for="text">Ingrese Contraseña:</label>
                        <input type="password" class="form-control" name="password" maxlength="15">
                    </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                <button type="submit" class="btn btn-primary">Registrar</button>
            </div>
            </form>
        </div>
    </div>
</div>

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



<script>
    $(document).ready(function () {
        document.getElementById("botonDesconectarse").style.visibility = "hidden";
        document.getElementById("textconverter").href = "index.jsp";
        if ($('#verificar').text() === "incorrecto") {
            document.getElementById("textoError").textContent = "Datos Incorrectos";
            $("#modalError").modal();
        }
        if ($('#registrar').text() === "registrar") {
            $("#modalRegistro").modal();
        }
        if ($('#registroValido').text() === "valido") {
            document.getElementById("textoGood").textContent = "Registro realizado correctamente.";
            $("#modalGood").modal();
        }
        if ($('#registroValido').text() === "invalido") {
            document.getElementById("textoError").textContent = "Error al Registrar, puede que el usuario ya exista";
            $("#modalError").modal();
        }
        if ($('#logueado').text() === "incorrecto") {
            document.getElementById("textoError").textContent = "Necesitas estar logueado para ver esta seccion";
            $("#modalError").modal();
        }
        if ($('#logueado').text() === "desconectado") {
            document.getElementById("textoGood").textContent = "Desconectado correctamente";
            $("#modalGood").modal();
        }
    });
</script>
