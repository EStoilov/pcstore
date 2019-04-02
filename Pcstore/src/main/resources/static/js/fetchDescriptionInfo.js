
   function openPersonModal(name) {
       $.ajax({
           url:"/person/" + name,
           success: function (data) {
               $("#categoryModalHolder").html(data);
               $("#categoryModal").modal("show");
           }
       });
   } ;


   <div id="personModal" class="modal fade" role="dialog" th:fragment="modalContents">
       <div class="modal-dialog">
       <div class="modal-content">
       <div class="modal-header">
       <h3 class="modal-title" th:text="${person.name}"></h3>
       </div>
       <div class="modal-body">
       <p th:text="${person.description}"></p>
       </div>
       <div class="modal-footer">
       <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
       </div>

       </div>
       </div>
       </div>