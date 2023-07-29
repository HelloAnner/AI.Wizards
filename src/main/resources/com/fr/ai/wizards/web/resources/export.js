BI.config("textarea.icon.click.handler", items => {
    items.push(() => {
        const client = Dec.Components.handlingClient;
        const task = client && client.model && client.model.currTask;
        if(task) {
            const templatePath = task.templatePath;
            console.log('定时调度选择的模板链接路径为:' + templatePath);

            //todo 根据模板路径获取推荐的内容
            const aiContent = 'AI推荐的内容测试XXXXXXX';
            client.store.setContentText(aiContent);

        } else {
            console.warn('定时调度任务不存在');
        }
    });

    return items;
});