/**
 * Created by Administrator on 2017/6/22.
 */
function startTime()
{
    var today=new Date()
    var year = today.getFullYear()
    var month = today.getMonth()+1
    var day = today.getDate()
    var h=today.getHours()
    var m=today.getMinutes()
    var s=today.getSeconds()
// add a zero in front of numbers<10
    month=checkTime(month)
    day=checkTime(day)
    m=checkTime(m)
    s=checkTime(s)
    document.getElementById('timeSpan').innerHTML=year+"-"+month+"-"+day+" "+h+":"+m+":"+s
    t=setTimeout('startTime()',500)
}

function checkTime(i)
{
    if (i<10)
    {i="0" + i}
    return i
}
