$(document).ready(function () {

    $('#ajouterbutton').prop('disabled',true);

    $("input[type='number']").change(function () {

        var agent = $("#Agent").val();
        var ascendant  = $("#Ascendant").val();
        var precompte=$("#precompte").val();
        var totalamount = $("#amount").val();


        if(Number(totalamount)===Number(agent)+Number(ascendant)+Number(precompte)){
            $('#ajouterbutton').prop('disabled', false);
        }else {
            $('#ajouterbutton').prop('disabled', true);

        }

    });


    $('select').selectpicker();

    $('#confirmButton').prop('disabled',true);
    $("input[type='checkbox']").change(function () {

        checked = $("input[type=checkbox]:checked").length;
        $('#confirmButton').prop('disabled',checked<1);

    });

    $('input[type=checkbox]').change(function(){
        var valchecked = $(this).attr('data-contractedcode');
        if (!$(this).is(checked)){
            $('input[type=checkbox]:not(:checked)').each(function () {
                var valnotchecked = $(this).attr('data-contractedcode');
                var id=$(this).attr('id');
                if (valnotchecked!==valchecked){
                    document.getElementById(id).disabled=true;

                }

            });

        }
    });




    $(function () {

        $('#confirmButtonModalPaiement').on('click', function () {

            $.ajax({
                type:"POST",
                url:"/Payer",
                data:  $('#formcheck').serialize(),
                dataType: "json",
                success:function (data){

                }

            });


        });






    });

    $('input[type=button]').on('click' , function(){
        var id=$(this).attr();

            var ReceptionDate =$('#ReceptionDateEdit').val();
            $('#ChangeDateEdit').attr('min',ReceptionDate);



            $("#ReceptionDateEdit").change("input", function() {

                var ReceptionDate =$(this).val();
                $('#ChangeDateEdit').attr('min',ReceptionDate);

            });

    });




});




