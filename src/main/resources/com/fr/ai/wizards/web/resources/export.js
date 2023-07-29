BI.config("textarea.icon.click.handler", items => {
    items.push(() => {
        const client = Dec.Components.handlingClient;
        const task = client && client.model && client.model.currTask;
        if(task) {
            const templatePath = task.templatePath;
            console.log('定时调度选择的模板链接路径为:' + templatePath);
        } else {
            console.warn('定时调度任务不存在');
        }
    });

    return items;
});