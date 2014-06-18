package com.clutch.dates.service;

import com.clutch.dates.StringToTime;
import com.clutch.dates.StringToTimeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:gchas@soft-project.pl">Grzegorz Chaś</a>
 *         $Rev: $, $LastChangedBy: $
 *         $LastChangedDate: $
 */
public class DateServiceImpl implements DateService {

    private static final Log log = LogFactory.getLog(DateServiceImpl.class);

    public Date parseStringToDate(String input) {

        try {

            if (input.contains(" ")) {

                String[] tokens = input.split(" ");

                if (tokens != null && tokens.length == 2) {

                    Pattern p0 = Pattern.compile("\\d{1,2}(\\.|\\/|\\-|\\:)\\d{1,2}(\\.|\\/|\\-|\\:)\\d{2,4}");
                    Matcher m0 = p0.matcher(tokens[0]);

                    Pattern p1 = Pattern.compile("\\d{2,4}(\\.|\\/|\\-|\\:)\\d{1,2}(\\.|\\/|\\-|\\:)\\d{1,2}");
                    Matcher m1 = p1.matcher(tokens[0]);

                    if (m0.matches() || m1.matches()) {

                        input = tokens[0];
                    }
                }
            }

            Pattern p = Pattern.compile("\\d{1,2}(\\.|\\/|\\-|\\:)\\d{1,2}(\\.|\\/|\\-|\\:)\\d{2,4}");
            Matcher m = p.matcher(input);

            if (m.matches()) {

                String[] tokens = createTokens(input);

                if (tokens != null && tokens.length == 3) {

                    String token0AsStr = tokens[0];
                    String token1AsStr = tokens[1];
                    String token2AsStr = tokens[2];

                    int token0 = Integer.parseInt(token0AsStr);
                    int token1 = Integer.parseInt(token1AsStr);

                    if (token0 > 12|| token1 <= 12) {

                        token0AsStr = String.valueOf(token1);
                        token1AsStr = String.valueOf(token0);
                    }
                    input = token0AsStr + "/" + token1AsStr + "/" + token2AsStr;
                }
            }
            StringToTime date = new StringToTime(input);
            return date.getCal().getTime();

        } catch (StringToTimeException ste) {
            log.debug(ste.getMessage());
            return null;
        }
    }

    private String[] createTokens(String input) {

        String result[] = null;

        if (input.contains(".")) {

            result = input.split("\\.");

        } else if (input.contains("/")) {

            result = input.split("/");

        } else if (input.contains("-")) {

            result = input.split("-");

        } else if (input.contains(":")) {

            result = input.split(":");
        }
        return result;
    }
}
