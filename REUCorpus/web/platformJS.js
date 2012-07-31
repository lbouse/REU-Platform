/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var sourceCnt = 0;

function addSrc()
{
    var url = document['mapping']['webURL'].value;
    if(url != '')
    {    
        var previousInnerHTML = new String();
        previousInnerHTML = document.getElementById('sourceList').innerHTML; 
        previousInnerHTML = previousInnerHTML.concat(url + "<input type='hidden' name='URL" 
            + sourceCnt + "' value='" + url + "' /><br /><br />"); 
        
        document.getElementById('div1').innerHTML = previousInnerHTML; 
    }
}