<html>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mishon.couriers.Entities.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
                            <!--Getting session here-->

                         


                                <c:if test="${sessionId==0}">
                                    <p>Login failed<br></p>
                                    <a href="/mycontext/index.jsp">Go back</a>

                                </c:if>

                                <c:if test="${type=='Manager'}">


                                    <h1>Manager Dashboard</h1>
                                    <h2>Routes and requests:</h2>
                                    <h3>Available Routes:</h3>
                                    <table border=1>
                                        <tbody>
                                            <tr>
                                                <td>ID</td>
                                                <td>&ensp;&ensp;Destination&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Cargo Type&ensp;&ensp;</td>
                                            </tr>
                                  <c:forEach items="${routeArr}" var="route">
                                                <tr>
                                                    <td>
                                                    <c:out value="${route.getId()}"/>
                                                    </td>
                                                    <td>
                                                      <c:out value="${route.getDestination()}"/>
                                                    </td>
                                                    <td>
                                                         <c:out value="${route.getCargo()}"/>
                                                    </td>
                                                </tr>

                                             </c:forEach>
                                        </tbody>
                                    </table>

                                    <form action="reqinsert" method="POST">
                                     <input type="hidden" name="sessionId" id="sessionId" value="101">
                                    <label for="name">Add new request</label><br />
                                     <input name="issuer" type="text" value="Issuer" required="required" pattern="[a-zA-Z0-9-]{1,30}"/> <br />
                                      <input name="date" type="text" value="Deadline"required="required" pattern="\d{4}\.\d{1,2}\.\d{1,2}" /> <br />
                                       <input name="req_capacity" type="text" value="Reqired Capacity(m3)"required="required" pattern="[0-9-]{1,30}"/> <br /> 
                                       <input name="price" type="text" value="Price" required="required" pattern="[0-9-]{1,30}"/> <br />
                                        <input name="route" type="text" value="Route number" required="required" pattern="[0-9]{1,30}"/> <br /> 
                                        <button type="submit" value="Submit">Submit</button>
                                        </form>

                                    <p><br /><br /></p>

                                    <h3>Unassigned Requests:</h3>
                                    <table border=1>
                                        <tbody>
                                            <tr>
                                                <td>ID</td>
                                                <td>&ensp;&ensp;Issuer&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Deadline&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Required Cap&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Price&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Assigned Car&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Route ID&ensp;&ensp;</td>
                                            </tr>
                                            <c:forEach items="${reqArr}" var="req">
                                                <tr>
                                                    <td>
                                                         <c:out value="${req.getId()}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${req.getIssuer()}"/>
                                                    </td>
                                                    <td>
                                                       <c:out value="${req.getDate()}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${req.getReqCapacity()}"/>
                                                    </td>
                                                    <td>
                                                       <c:out value="${req.getPrice()}"/>
                                                    </td>
                                                    <td>
                                                       <c:out value="${req.getCar()}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${req.getRoute()}"/>
                                                    </td>
                                                </tr>

     </c:forEach>
                                        </tbody>
                                    </table>
                                    <form action="dget" method="GET">
                                     <input type="hidden" name="sessionId" id="sessionId" value="102">
                                    <label for="name">Find driver</label><br />
                                     <input name="reqID" type="text" value="Request ID" required="required" pattern="[0-9-]{1,30}"/>
                                      <button type="submit" value="Submit">Find Cars</button>
                                      </form>
                                      <c:if test="${cars != null}">
                                    <h4>Current Request: <c:out value="${currentRequest}"/></h4>
                                    <h3>Suitable Drivers:</h3>
                                    <table border=1>
                                        <tbody>
                                            <tr>
                                                <td>ID</td>
                                                <td>&ensp;&ensp;Car&ensp;&ensp;</td>
                                                <td>&ensp;Car Type&ensp;</td>
                                                <td>&ensp;&ensp;Capacity&ensp;&ensp;</td>
                                            </tr>

        <c:forEach items="${cars}" var="car">
                                                <tr>
                                                    <td>
                                                      <c:out value="${car.getId()}"/>
                                                    </td>
                                                    <td>
                                                       <c:out value="${car.getCarName()}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${car.getCarType()}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${car.getCapacity()}"/>
                                                    </td>

                                                </tr>
</c:forEach>
      
                                        </tbody>
                                    </table>
                                    <form action="dassign" method="POST">
                                     <input type="hidden" name="sessionId" id="sessionId" value="103">
                                     <input type="hidden" name="reqID" id="reqID" value=<c:out value="${currentRequest}"/>>
                                    <label for="name">Assign driver</label><br />
                                     <input name="selectedDriver" type="text" value="Select Driver"required="required" pattern="[0-9-]{1,30}" />
                                      <button type="submit" value="Submit">Assign Driver</button>
                                      </form>
                                    <!-- For manager - create buttons for request creation and textboxes for driver assignment-->
 </c:if>
                                </c:if>

    <c:if test="${type=='Driver'}">
 <h1>Driver Dashboard</h1>
 <h2>Car fix check:<c:out value="${currentPrice}"/></h2>
                                    <h3>Assigned requests:</h3>

                               
	<form action="dfix" method="POST">
     <input type="hidden" name="sessionId" id="sessionId" value="106">
	<button type="submit" value="Submit">Car fixed</button>
       <input type="hidden" name="sessionId" id="sessionId" value="106">
     <input type="hidden" name="currentUser" id="currentUser" value=<c:out value="${currentUser}"/>>
                        
</form>
  



  <p>
      <br>
    </p>
    <table border=1>
                                        <tbody>
                                            <tr>
                                                <td>ID</td>
                                                <td>&ensp;&ensp;Issuer&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Deadline&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Required Cap&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Price&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Assigned Car&ensp;&ensp;</td>
                                                <td>&ensp;&ensp;Route ID&ensp;&ensp;</td>
                                            </tr>
                                              <c:forEach items="${reqArr}" var="req">
                                                <tr>
                                                    <td>
                                                         <c:out value="${req.getId()}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${req.getIssuer()}"/>
                                                    </td>
                                                    <td>
                                                       <c:out value="${req.getDate()}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${req.getReqCapacity()}"/>
                                                    </td>
                                                    <td>
                                                       <c:out value="${req.getPrice()}"/>
                                                    </td>
                                                    <td>
                                                       <c:out value="${req.getCar()}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${req.getRoute()}"/>
                                                    </td>
                                                </tr>

     </c:forEach>
      </tbody></table>
<form action="dcomplete" method="POST">
 <input type="hidden" name="sessionId" id="sessionId" value="104">
  <input type="hidden" name="currentUser" id="currentUser" value=<c:out value="${currentUser}"/>>
<input name="reqID" type="text" value="Id" /> <br /> 
	<input name="expense" type="text" value="Expenses" pattern="[a-zA-Z0-9-]{1,30}"/> <br /> 
  <input name="completed" type="text" value="Completion message" pattern="[0-9-]{1,30}"/> <br /> 

	<button type="submit" value="Submit">Submit</button>
</form>




    </c:if>






                                   

</html>