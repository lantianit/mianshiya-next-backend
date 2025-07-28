package com.yupi.mianshiya.blackfilter;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

/**
 * 黑名单过滤工具类
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Slf4j
public class BlackIpUtils {

    private static BitMapBloomFilter bloomFilter;

    // 判断 ip 是否在黑名单里
    public static boolean isBlackIp(String ip) {
        if (bloomFilter == null) {
            log.warn("布隆过滤器未初始化，IP {} 默认允许访问", ip);
            return false;
        }
        
        boolean isBlack = bloomFilter.contains(ip);
        log.debug("检查IP {} 是否在黑名单中: {}", ip, isBlack);
        return isBlack;
    }

    /**
     * 重建 ip 黑名单
     *
     * @param configInfo
     */
    public static void rebuildBlackIp(String configInfo) {
        log.info("开始重建IP黑名单，配置信息: {}", configInfo);
        
        if (StrUtil.isBlank(configInfo)) {
            configInfo = "{}";
            log.warn("配置信息为空，使用默认空配置");
        }
        
        // 解析 yaml 文件
        Yaml yaml = new Yaml();
        Map map = yaml.loadAs(configInfo, Map.class);
        // 获取 IP 黑名单
        List<String> blackIpList = (List<String>) map.get("blackIpList");
        
        log.info("解析到的黑名单IP列表: {}", blackIpList);
        
        // 加锁防止并发
        synchronized (BlackIpUtils.class) {
            if (CollUtil.isNotEmpty(blackIpList)) {
                // 注意构造参数的设置
                BitMapBloomFilter bitMapBloomFilter = new BitMapBloomFilter(958506);
                for (String blackIp : blackIpList) {
                    bitMapBloomFilter.add(blackIp);
                    log.debug("添加黑名单IP: {}", blackIp);
                }
                bloomFilter = bitMapBloomFilter;
                log.info("黑名单重建完成，共添加 {} 个IP", blackIpList.size());
            } else {
                bloomFilter = new BitMapBloomFilter(100);
                log.info("黑名单为空，创建默认布隆过滤器");
            }
        }
    }
}
