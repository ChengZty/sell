//录入初始化
function initData(){
    $('#jobidall').prop('checked', 'checked');
    //选择店铺
    $('#selectStoreAll').prop('checked', 'checked');
    $('input.stores').prop('checked', 'checked');

    $('tr.th-row').each(function(index, domEle){
        var $tr = $(domEle);
        var curTaskTitle = $tr.find('input.jobTitle').val().trim();
        var $inputCheckBox = $tr.find('input.th-job-id');
        var jobId = $inputCheckBox.val().trim();
        var $inputDayTime = $tr.find('input.dayTime');
        var $inputJobNum = $tr.find('input.jobNum');
        var $inputGoldNum = $tr.find('input.goldNum');
        var $inputGoldTime = $tr.find('input.goldTime');
        
        $inputCheckBox.prop('checked', 'checked');
        $inputCheckBox.parent().find('input[name*=status]').val('A');

        switch(jobId){
            case '11':
                $inputDayTime.val('1');
                $inputGoldNum.val('50');
                $inputGoldTime.val('1');
                $inputJobNum.val('5');
            break;
            case '12':
                $inputDayTime.val('1');
                $inputGoldNum.val('30');
                $inputGoldTime.val('1');
                $inputJobNum.val('2');
            break;
            /*case '13':
                $inputDayTime.val('1');
                $inputGoldNum.val('5');
                $inputGoldTime.val('1');
                $inputJobNum.val('2');
            break;*/
            case '14':
                $inputDayTime.val('1');
                $inputGoldNum.val('50');
                $inputGoldTime.val('1');
                $inputJobNum.val('3');
            break;
            /*case '15':
                $inputDayTime.val('1');
                $inputGoldNum.val('8');
                $inputGoldTime.val('1');
                $inputJobNum.val('1');
            break;*/
            case '16':
                $inputDayTime.val('1');
                $inputGoldNum.val('10');
                $inputGoldTime.val('1');
                $inputJobNum.val('3');
            break;
            case '17':
                $inputDayTime.val('1');
                $inputGoldNum.val('30');
                $inputGoldTime.val('1');
                $inputJobNum.val('2');
            break;
            case '18':
                $inputDayTime.val('1');
                $inputGoldNum.val('30');
                $inputGoldTime.val('1');
                $inputJobNum.val('2');
            break;
            case '19':
                $inputDayTime.val('1');
                $inputGoldNum.val('60');
                $inputGoldTime.val('1');
                $inputJobNum.val('2');
            break;
            case '20':
                $inputDayTime.val('1');
                $inputGoldNum.val('60');
                $inputGoldTime.val('1');
                $inputJobNum.val('1');
            break;
            case '21':
                $inputDayTime.val('1');
                $inputGoldNum.val('50');
                $inputGoldTime.val('1');
                $inputJobNum.val('2');
            break;
            case '22':
                $inputDayTime.val('1');
                $inputGoldNum.val('50');
                $inputGoldTime.val('1');
                $inputJobNum.val('2');
            break;
            case '23':
                $inputDayTime.val('1');
                $inputGoldNum.val('30');
                $inputGoldTime.val('1');
                $inputJobNum.val('1');
            break;
            case '24':
                $inputDayTime.val('1');
                $inputGoldNum.val('30');
                $inputGoldTime.val('1');
                $inputJobNum.val('10');
            break;
            default:
        }
    });
    
}

//编辑初始化
function initUpdateData(){
    $('tr.th-row').each(function(index, domEle){
        var $tr = $(domEle);
        var curTaskTitle = $tr.find('input.jobTitle').val().trim();
        var $inputCheckBox = $tr.find('input.th-job-id');
        var jobId = $inputCheckBox.val().trim();
        var $inputDayTime = $tr.find('input.dayTime');
        var $inputJobNum = $tr.find('input.jobNum');
        var $inputGoldNum = $tr.find('input.goldNum');
        var $inputGoldTime = $tr.find('input.goldTime');
        var $inputCycleEveryday = $tr.find('input[name*=cycle][value=1]');
        var $inputPunishr = $tr.find('input[name*=punishr][value=0]');

        if(!$inputCheckBox.prop('checked')){
            $inputCheckBox.parent().find('input[name*=status]').val('I');
            $inputCycleEveryday.prop('checked', 'checked');
            $inputPunishr.prop('checked', 'checked');
            
            switch(jobId){
	            case '11':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('50');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('5');
	            break;
	            case '12':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('30');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('2');
	            break;
	            /*case '13':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('5');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('2');
	            break;*/
	            case '14':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('50');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('3');
	            break;
	            /*case '15':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('8');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('1');
	            break;*/
	            case '16':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('10');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('3');
	            break;
	            case '17':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('30');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('2');
	            break;
	            case '18':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('30');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('2');
	            break;
	            case '19':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('60');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('2');
	            break;
	            case '20':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('60');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('1');
	            break;
	            case '21':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('50');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('2');
	            break;
	            case '22':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('50');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('2');
	            break;
	            case '23':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('30');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('1');
	            break;
	            case '24':
	                $inputDayTime.val('1');
	                $inputGoldNum.val('30');
	                $inputGoldTime.val('1');
	                $inputJobNum.val('10');
	            break;
	            default:
	        }
        }
    });
}

//返回
function goBack(){
    document.location="tJobModelController.do?list";
}
//全选任务
function selectAllJob(){
    var checklist = document.querySelectorAll('.th-job-id');
    if(document.getElementById("jobidall").checked)
    {
    for(var i=0;i<checklist.length;i++)
    {
        console.log(checklist[i]);
        checklist[i].checked = 1;
        checklist[i].parentNode.querySelector('input[name*=status]').value = 'A';
    } 
    }else{
        for(var j=0;j<checklist.length;j++)
        {
            console.log(checklist[j]);
            checklist[j].checked = 0;
            checklist[j].parentNode.querySelector('input[name*=status]').value = 'I';
        }
    }
}

selectJob();
//选任务
function selectJob(){
    var checklist = document.querySelectorAll('.th-job-id');
    var flage = true;
    for(var i=0;i<checklist.length;i++){
        if(!checklist[i].checked){
            flage = false;
        }
    }
    if(flage){
        document.getElementById("jobidall").checked = 1;
    }else{
        document.getElementById("jobidall").checked = 0;
    }
}
//全选店铺
function selectAllStore(){
var checklist = document.getElementsByName ("stores");
if(document.getElementById("selectStoreAll").checked)
{
for(var i=0;i<checklist.length;i++)
{
    checklist[i].checked = 1;
} 
}else{
for(var j=0;j<checklist.length;j++)
{
    checklist[j].checked = 0;
}
}
}

//选店铺
selectStore();
function selectStore(){
    var checklist = document.getElementsByName ("stores");
    var flage = true;
    for(var i=0;i<checklist.length;i++){
        if(!checklist[i].checked){
            flage = false;
        }
    }
    if(flage){
        document.getElementById("selectStoreAll").checked = 1;
    }else{
        document.getElementById("selectStoreAll").checked = 0;
    }
}

$('body').on('click', '.stores', function(){
    selectStore();
});
    
$('body').on('focus', 'input.dayTime', function(){
    var $_this = $(this);
    $_this.parent().find('input[type=radio][value=1]').click();
});

$('body').on('focus', '#startTime, #endTime', function(){
    var $_this = $(this);
    $_this.parent().find('input[type=radio][value=0]').click();
});

$('body').on('focus', 'input.punish', function(){
    var $_this = $(this);
    $_this.parent().find('input[type=radio][value=1]').click();
});
var showMsg = function(msg){
    tip(msg);
    flag = false;
};
//检查数据
function checkData(){
    var flag = true;
    //检查任务组名
    var taskName = $('input.name').val().trim();
    if(taskName === ''){
        tip('请输入任务组名');
        flag = false
        return flag;
    }
    var startTimeStr = $('#startTime').val().trim();
    var endTimeStr = $('#endTime').val().trim();
    if(startTimeStr === ''){
        showMsg('请输入任务开始时间');
        return false;
    }else if(endTimeStr === ''){
        showMsg('请输入任务结束时间');
        return false;
    }
    
    var $checkedInput = $('input.th-job-id:checked');
    $checkedInput.each(function(index, domEle){
        var $domEle = $(domEle);
        var $parent = $domEle.parents('.th-row');
        var taskTitle = $parent.find('.jobTitle').val().trim();
        var showMsg = function(msg){
            tip(msg);
            flag = false;
        };
        
        //检查任务时间
        /*var $taskTime = $parent.find('.task-time');
        var $inputChcked = $taskTime.find('input[type=radio]:checked');
        var taskTimeValue = $inputChcked.val().trim();
        if(taskTimeValue === '0'){ //时间范围
            var startTimeStr = $parent.find('input.startTime').val().trim();
            var endTimeStr = $parent.find('input.endTime').val().trim();
            if(startTimeStr === ''){
                showMsg('请输入"' + taskTitle + '"中的"任务开始时间"');
                return false;
            }else if(endTimeStr === ''){
                showMsg('请输入' + taskTitle + '中的任务结束时间');
                return false;
            }
            
        }else if(taskTimeValue === '1'){ //每一天
            var dayTimeStr = $parent.find('input.dayTime').val().trim();
            if(dayTimeStr === ''){
                showMsg('请输入"' + taskTitle + '"中的"任务时间"');
                return false;
            }
        }*/
        //检查任务完成标准
        var finishedStandardStr = $parent.find('.task-finished-standard .jobNum').val().trim();
        if(finishedStandardStr === ''){
            showMsg('请输入"' + taskTitle + '"中的"任务完成标准"');
            return false;
        }
        //检查任务完成奖励标准
        var $rewardStandard = $parent.find('.task-reward-standard');
        var goldNum = $rewardStandard.find('.goldNum').val().trim();
//        var goldTime = $rewardStandard.find('.goldTime').val().trim();
        if(goldNum === ''){
            showMsg('请输入"' + taskTitle + '"中的"任务完成奖励标准"');
            return false;
        }
        //检查任务完成处罚标准
        /*var $punishStandard = $parent.find('.task-punish-standard');
        var punishValue = $punishStandard.find('input[type=radio]:checked').val().trim();
        var punishGold = $punishStandard.find('input[type=text]').val().trim();
        if(punishValue === '1' && punishGold === ''){
            showMsg('请输入"' + taskTitle + '"中的"任务完成处罚标准"');
            return false;
        }*/
    });

    return flag;
}

//监听任务checkbox的变化，如果选中，则是'A'；如果不选中，则是'I'
$('body').on('change', 'input.th-job-id', function(){
    var $this = $(this);
    var $status = $this.parent().find('input[name*=status]');
    if($this.prop('checked')){  //如果选中
        $status.val('A');
    }else{
        $status.val('I');  
    }
});

