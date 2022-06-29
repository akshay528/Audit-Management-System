# CDE-MFPE-Project-Audit-Management-System

## Authors :

<table>
  <tr>
      <td>
        <a href="https://github.com/DRoy7">Deep Roy</a>
        </td>
      <td>
        <a href="https://github.com/sriharish252">Sri Harish</a>
        </td>
      <td>
        <a href="https://github.com/Kamalesh8">Kamalesh R</a>
        </td>
      <td>
        <a href="https://github.com/Megha0699">Megha S</a>
        </td>
      <td>
        <a href="https://github.com/greninja199">Praduman Kumar</a>
        </td>
    </tr>
</table>

## Module Name::

* ### Audit-Authorization-Microservice :
  This module is used for doing the **Authentication** and **Authorization** part of our project. 
  This microsevice provides the endpoints for authentication and validation. This MS creates JWTs(JSON Web Token)
  for a authenticated user who is in Database and then it validates the user based on the JWT token passed in the
  "Authentication"-Request-Header.("Bearer j.w.t...")

  * #### --Endpoints : 
    <table>
        <thead>
            <th>Method</th>
            <th>Endpoint Path</th>
            <th>Returns</th>
        </thead>
        <tbody>
            <tr>
                <td>GET</td>
                <td>/health-check</td>
                <td>String</td>
            </tr>
            <tr>
                <td>POST</td>
                <td>/authenticate</td>
                <td>ResponseEntity of String(JWT token)</td>
            </tr>
            <tr>
                <td>POST</td>
                <td>/validate</td>
                <td>ResponseEntity of AuthenticationResponse</td>
            </tr>
        </tbody>
    </table>

  * #### --Dependencies on Other microsevices : **None**

  * #### --Application Properties Toggle :<br/>
      JAR name = MFPE-AuthorizationMS-JAR<br/>
      server.port=8100<br/>
      server.servlet.context-path=/auth<br/>
      User Database : H2(In-Memory)<br/>
      Data about authenticated users provided in : data.sql
      JWT token duration = 5mins(given)...You can change the properties from the application.properties file

