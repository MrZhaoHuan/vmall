import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-27 10:51
 * @描述  solr集群测试
 */
public class SolrCloudTest {

    public static void main(String[] args) throws SolrServerException {
        CloudSolrServer solrServer = new CloudSolrServer("192.168.92.129:2181,192.168.92.129:2182,192.168.92.129:2183");
        solrServer.setDefaultCollection("collection2");
        SolrQuery solrQ = new SolrQuery();
        solrQ.setQuery("*:*");
        QueryResponse response = solrServer.query(solrQ);
        SolrDocumentList results = response.getResults();
        System.out.println(results.getNumFound());
    }
}
