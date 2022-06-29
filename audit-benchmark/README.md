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

## Module Name :

* ### Audit-Benchmark-Microservice :
  This module is used for storing and retrieving the **Number of acceptable NOs** for each **Audit Type** from the H2 in-memory database.
  This microsevice is used by the Audit-Severity Microservice to evaluate the status of a project and provide an appropriate response to the client.

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
                <td>/AuditBenchmark</td>
                <td>List of AuditBenchmark</td>
            </tr>
        </tbody>
    </table>

  * #### --Dependencies on Other microsevices : **Audit-Authorization Microservice**

  * #### --Application Properties Toggle :<br/>
      spring.application.name=AuditBenchmark<br/>
      server.port=8250<br/>
      server.servlet.context-path=/benchmark<br/>
      User Database : H2(In-Memory)<br/>
